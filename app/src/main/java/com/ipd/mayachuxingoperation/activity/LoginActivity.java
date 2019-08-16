package com.ipd.mayachuxingoperation.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.base.BaseActivity;
import com.ipd.mayachuxingoperation.bean.CaptchaBean;
import com.ipd.mayachuxingoperation.bean.LoginBean;
import com.ipd.mayachuxingoperation.contract.LoginContract;
import com.ipd.mayachuxingoperation.presenter.LoginPresenter;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;
import com.ipd.mayachuxingoperation.utils.SPUtil;
import com.ipd.mayachuxingoperation.utils.ToastUtil;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxingoperation.common.config.IConstants.AVATAR;
import static com.ipd.mayachuxingoperation.common.config.IConstants.CITY;
import static com.ipd.mayachuxingoperation.common.config.IConstants.IS_LOGIN;
import static com.ipd.mayachuxingoperation.common.config.IConstants.NAME;
import static com.ipd.mayachuxingoperation.common.config.IConstants.TOKEN;
import static com.ipd.mayachuxingoperation.utils.VerifyUtils.isMobileNumber;
import static com.ipd.mayachuxingoperation.utils.isClickUtil.isFastClick;

/**
 * Description ：登录
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    @BindView(R.id.et_captcha)
    MaterialEditText etCaptcha;
    @BindView(R.id.bt_captcha)
    SuperButton btCaptcha;
    @BindView(R.id.cb_login)
    CheckBox cbLogin;

    private long firstTime = 0;
    private CountDownButtonHelper mCountDownHelper; //倒计时

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.with(this).statusBarDarkFont(false).init();

        mCountDownHelper = new CountDownButtonHelper(btCaptcha, 60);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    //双击退出程序
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtil.showShortToast(getResources().getString(R.string.click_out_again));
            firstTime = secondTime;
        } else {
            ApplicationUtil.getManager().exitApp();

        }
    }

    @Override
    protected void onDestroy() {
        mCountDownHelper.cancel();
        super.onDestroy();
    }

    @OnClick({R.id.bt_captcha, R.id.bt_agreement, R.id.rv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_captcha:
                if (etPhone.getText().toString().trim().length() == 11 && isMobileNumber(etPhone.getText().toString().trim())) {
                    TreeMap<String, String> captchaMap = new TreeMap<>();
                    captchaMap.put("phone", etPhone.getText().toString().trim());
                    captchaMap.put("static", "1");
                    getPresenter().getCaptcha(captchaMap, false, false);
                } else
                    ToastUtil.showLongToast("请填写手机号码");
                break;
            case R.id.bt_agreement:
                startActivity(new Intent(LoginActivity.this, WebViewActivity.class).putExtra("h5Type", 3));
                break;
            case R.id.rv_login:
                if (isFastClick()) {
                    if (cbLogin.isChecked()) {
                        if (etPhone.getText().toString().trim().length() == 11 && isMobileNumber(etPhone.getText().toString().trim()) && etCaptcha.getText().toString().trim().length() == 6) {
                            TreeMap<String, String> loginMap = new TreeMap<>();
                            loginMap.put("phone", etPhone.getText().toString().trim());
                            loginMap.put("code", etCaptcha.getText().toString().trim());
                            getPresenter().getLogin(loginMap, false, false);
                        } else
                            ToastUtil.showLongToast("请填写正确的登录信息");
                    } else
                        ToastUtil.showLongToast("请同意用户协议!");
                }
                break;
        }
    }

    @Override
    public void resultCaptcha(CaptchaBean data) {
        if (data.getCode() == 200)
            mCountDownHelper.start();
        else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultLogin(LoginBean data) {
        if (data.getCode() == 200) {
            ApplicationUtil.getManager().finishActivity(MainActivity.class);
            SPUtil.put(this, IS_LOGIN, "is_login");
            SPUtil.put(this, TOKEN, data.getData().getToken());
            SPUtil.put(this, AVATAR, data.getData().getHeaderUrl());
            SPUtil.put(this, NAME, data.getData().getName());
            SPUtil.put(this, CITY, data.getData().getArea());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
