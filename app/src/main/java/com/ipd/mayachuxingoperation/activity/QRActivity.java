package com.ipd.mayachuxingoperation.activity;

import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.base.BaseActivity;
import com.ipd.mayachuxingoperation.bean.ControlLockBean;
import com.ipd.mayachuxingoperation.bean.ElectricBoxBean;
import com.ipd.mayachuxingoperation.common.view.InputDialog;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.LockContract;
import com.ipd.mayachuxingoperation.presenter.LockPresenter;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;
import com.ipd.mayachuxingoperation.utils.L;
import com.ipd.mayachuxingoperation.utils.ToastUtil;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.xuexiang.xqrcode.ui.CaptureFragment;
import com.xuexiang.xqrcode.util.QRCodeAnalyzeUtils;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxingoperation.utils.StringUtils.identical;

/**
 * Description ：扫码开锁
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/4.
 */
public class QRActivity extends BaseActivity<LockContract.View, LockContract.Presenter> implements LockContract.View {

    @BindView(R.id.tv_qr)
    TopView tvQr;
    @BindView(R.id.cb_flash)
    MaterialCheckBox cbFlash;
    @BindView(R.id.tv_flash)
    AppCompatTextView tvFlash;

    private String carNum;//扫描的车辆编号
    private int lockType;//1: 开锁， 2: 关锁， 3: 开电箱

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr;
    }

    @Override
    public LockContract.Presenter createPresenter() {
        return new LockPresenter(this);
    }

    @Override
    public LockContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvQr);

        CaptureFragment captureFragment = XQRCode.getCaptureFragment(R.layout.activity_custom_capture, true, 1000);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        captureFragment.setCameraInitCallBack(cameraInitCallBack);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        //设置相机的自动聚焦间隔
        XQRCode.setAutoFocusInterval(1500L);

        lockType = getIntent().getIntExtra("lockType", 0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 照相机初始化监听
     */
    CaptureFragment.CameraInitCallBack cameraInitCallBack = new CaptureFragment.CameraInitCallBack() {
        @Override
        public void callBack(@Nullable Exception e) {
            if (e != null) {
                CaptureActivity.showNoPermissionTip(QRActivity.this);
            }
        }
    };

    /**
     * 二维码解析回调函数
     */
    QRCodeAnalyzeUtils.AnalyzeCallback analyzeCallback = new QRCodeAnalyzeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap bitmap, String result) {
            handleAnalyzeSuccess(bitmap, result);
        }

        @Override
        public void onAnalyzeFailed() {
            handleAnalyzeFailed();
        }
    };

    /**
     * 处理扫描成功
     *
     * @param bitmap
     * @param result
     */
    protected void handleAnalyzeSuccess(Bitmap bitmap, String result) {
        L.i("result = " + result);
        carNum = identical(result, "IMEI:", " ").replaceAll("IMEI:", "").trim();
        if (lockType != 3) {
            TreeMap<String, String> controlLockMap = new TreeMap<>();
            controlLockMap.put("imei", carNum);
            controlLockMap.put("type", lockType - 1 + "");
            getPresenter().getControlLock(controlLockMap, false, false);
        } else {
            TreeMap<String, String> electricBoxMap = new TreeMap<>();
            electricBoxMap.put("imei", carNum);
            electricBoxMap.put("type", "0");
            getPresenter().getElectricBox(electricBoxMap, false, false);
        }
    }

    /**
     * 处理解析失败
     */
    protected void handleAnalyzeFailed() {
        ToastUtil.showLongToast("扫描失败，请重试");
    }

    @OnClick({R.id.bt_input_car_num, R.id.cb_flash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_input_car_num:
                new InputDialog(this) {
                    @Override
                    public void confirm(String str) {
                        if (lockType != 3) {
                            TreeMap<String, String> controlLockMap = new TreeMap<>();
                            controlLockMap.put("imei", str);
                            controlLockMap.put("type", lockType - 1 + "");
                            getPresenter().getControlLock(controlLockMap, false, false);
                        } else {
                            TreeMap<String, String> electricBoxMap = new TreeMap<>();
                            electricBoxMap.put("imei", str);
                            electricBoxMap.put("type", "0");
                            getPresenter().getElectricBox(electricBoxMap, false, false);
                        }
                    }
                }.show();
                break;
            case R.id.cb_flash:
                if (cbFlash.isChecked()) {
                    XQRCode.switchFlashLight(true);
                    tvFlash.setText("关灯");
                } else
                    try {
                        XQRCode.switchFlashLight(false);
                        tvFlash.setText("开灯");
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        ToastUtil.showLongToast("设备不支持闪光灯!");
                    }
                break;
        }
    }

    @Override
    public void resultControlLock(ControlLockBean data) {
        if (data.getCode() == 200) {
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultElectricBox(ElectricBoxBean data) {
        if (data.getCode() == 200) {
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
