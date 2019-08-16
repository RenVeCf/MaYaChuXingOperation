package com.ipd.mayachuxingoperation.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxingoperation.R;
import com.ipd.mayachuxingoperation.base.BaseActivity;
import com.ipd.mayachuxingoperation.bean.ModifyNameBean;
import com.ipd.mayachuxingoperation.bean.UploadHeadBean;
import com.ipd.mayachuxingoperation.common.view.TopView;
import com.ipd.mayachuxingoperation.contract.PersonalDocumentContract;
import com.ipd.mayachuxingoperation.presenter.PersonalDocumentPresenter;
import com.ipd.mayachuxingoperation.utils.ApplicationUtil;
import com.ipd.mayachuxingoperation.utils.SPUtil;
import com.ipd.mayachuxingoperation.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

import static com.ipd.mayachuxingoperation.common.config.IConstants.AVATAR;
import static com.ipd.mayachuxingoperation.common.config.IConstants.CITY;
import static com.ipd.mayachuxingoperation.common.config.IConstants.NAME;
import static com.ipd.mayachuxingoperation.common.config.IConstants.REQUEST_CODE_91;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.BASE_LOCAL_URL;
import static com.ipd.mayachuxingoperation.fragment.HomeFragment.getImageRequestBody;
import static com.ipd.mayachuxingoperation.utils.isClickUtil.isFastClick;

/**
 * Description ：个人资料
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class PersonalDocumentActivity extends BaseActivity<PersonalDocumentContract.View, PersonalDocumentContract.Presenter> implements PersonalDocumentContract.View {

    @BindView(R.id.tv_personal_document)
    TopView tvPersonalDocument;
    @BindView(R.id.riv_head)
    RadiusImageView rivHead;
    @BindView(R.id.tv_name)
    SuperTextView tvName;
    @BindView(R.id.tv_city)
    SuperTextView tvCity;

    private String headImgUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_document;
    }

    @Override
    public PersonalDocumentContract.Presenter createPresenter() {
        return new PersonalDocumentPresenter(this);
    }

    @Override
    public PersonalDocumentContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvPersonalDocument);
    }

    @Override
    public void initData() {
        Glide.with(this).load(BASE_LOCAL_URL + SPUtil.get(this, AVATAR, "")).apply(new RequestOptions().placeholder(R.drawable.ic_default_head)).into(rivHead);
        tvName.setRightString(SPUtil.get(this, NAME, "") + "");
        tvCity.setRightString(SPUtil.get(this, CITY, "") + "");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    headImgUrl = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
                    TreeMap<String, RequestBody> map = new TreeMap<>();
                    map.put("file\";filename=\"" + ".jpeg", getImageRequestBody(headImgUrl));
                    getPresenter().getUploadHead(map, false, false);
                    break;
                case REQUEST_CODE_91:
                    tvName.setRightString(data.getStringExtra("modify_name"));
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra("modify_head", headImgUrl).putExtra("modify_name", tvName.getRightString()));
        finish();
    }

    @OnClick({R.id.ll_head, R.id.tv_name, R.id.ll_top_back, R.id.rv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)// 最大图片选择数量 int
                        .isCamera(true)
                        .compress(true)
                        .minimumCompressSize(100)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_name:
                startActivityForResult(new Intent(this, NameActivity.class).putExtra("name", tvName.getRightString()), REQUEST_CODE_91);
                break;
            case R.id.ll_top_back:
                setResult(RESULT_OK, new Intent().putExtra("modify_head", headImgUrl).putExtra("modify_name", tvName.getRightString()));
                finish();
                break;
            case R.id.rv_sign_out:
                if (isFastClick()) {
                    ApplicationUtil.getManager().finishActivity(MainActivity.class);
                    //清除所有临时储存
                    SPUtil.clear(ApplicationUtil.getContext());
                    startActivity(new Intent(this, PersonalDocumentActivity.class));
                    finish();
                }
                break;
        }
    }

    @Override
    public void resultModifyName(ModifyNameBean data) {

    }

    @Override
    public void resultUploadHead(UploadHeadBean data) {
        if (data.getCode() == 200) {
            SPUtil.put(this, AVATAR, headImgUrl);
            Glide.with(this)
                    .load(headImgUrl)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            rivHead.setImageDrawable(resource);
                        }
                    });
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
