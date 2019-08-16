package com.ipd.mayachuxingoperation.activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.base.BaseActivity;
import com.ipd.mayachuxingoperation.bean.MalfunctionCarDetailsBean;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.MalfunctionCarDetailsContract;
import com.ipd.mayachuxingoperation.presenter.MalfunctionCarDetailsPresenter;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;
import com.ipd.mayachuxingoperation.utils.ToastUtil;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxingoperation.common.config.UrlConfig.BASE_LOCAL_URL;

/**
 * Description ：暂停车辆详情
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/8.
 */
public class PauseCarDetailsActivity extends BaseActivity<MalfunctionCarDetailsContract.View, MalfunctionCarDetailsContract.Presenter> implements MalfunctionCarDetailsContract.View {

    @BindView(R.id.tv_pause_car_details)
    TopView tvPauseCarDetails;
    @BindView(R.id.tv_car_code)
    SuperTextView tvCarCode;
    @BindView(R.id.tv_applicant)
    SuperTextView tvApplicant;
    @BindView(R.id.tv_application_time)
    SuperTextView tvApplicationTime;
    @BindView(R.id.tv_malfunction_type)
    SuperTextView tvMalfunctionType;
    @BindView(R.id.tv_details_tx)
    SuperTextView tvDetailsTx;
    @BindView(R.id.tv_pause_time)
    SuperTextView tvPauseTime;
    @BindView(R.id.iv_pause)
    RadiusImageView ivPause;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pause_car_details;
    }

    @Override
    public MalfunctionCarDetailsContract.Presenter createPresenter() {
        return new MalfunctionCarDetailsPresenter(this);
    }

    @Override
    public MalfunctionCarDetailsContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvPauseCarDetails);
    }

    @Override
    public void initData() {
        TreeMap<String, String> malfunctionCarDetailsMap = new TreeMap<>();
        malfunctionCarDetailsMap.put("id", getIntent().getStringExtra("id"));
        getPresenter().getMalfunctionCarDetails(malfunctionCarDetailsMap, false, false);
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.iv_pause)
    public void onViewClicked() {

    }

    @Override
    public void resultMalfunctionCarDetails(MalfunctionCarDetailsBean data) {
        if (data.getCode() == 200) {
            tvCarCode.setRightString(data.getData().getId());
            tvApplicant.setRightString(data.getData().getUser());
            tvApplicationTime.setRightString(data.getData().getTime());
            tvMalfunctionType.setRightString(data.getData().getType());
            tvDetailsTx.setRightString(data.getData().getSupplement());
            tvPauseTime.setRightString(data.getData().getEnd_time());
            Glide.with(this).load(BASE_LOCAL_URL + data.getData().getUrl()).apply(new RequestOptions()).into(ivPause);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
