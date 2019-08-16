package com.ipd.mayachuxingoperation.activity;

import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;

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

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxingoperation.common.config.IConstants.NAME;
import static com.ipd.mayachuxingoperation.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxingoperation.utils.isClickUtil.isFastClick;

/**
 * Description ：昵称
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class NameActivity extends BaseActivity<PersonalDocumentContract.View, PersonalDocumentContract.Presenter> implements PersonalDocumentContract.View {

    @BindView(R.id.tv_name)
    TopView tvName;
    @BindView(R.id.et_name)
    AppCompatEditText etName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_name;
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
        ImmersionBar.setTitleBar(this, tvName);

        if (!isEmpty(getIntent().getStringExtra("name")))
            etName.setText(getIntent().getStringExtra("name"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.rv_confirm)
    public void onViewClicked() {
        if (isFastClick() && !isEmpty(etName.getText().toString().trim())) {
            TreeMap<String, String> modifyNameMap = new TreeMap<>();
            modifyNameMap.put("name", etName.getText().toString().trim());
            getPresenter().getModifyName(modifyNameMap, false, false);
        }
    }

    @Override
    public void resultModifyName(ModifyNameBean data) {
        if (data.getCode() == 200) {
            SPUtil.put(this, NAME, etName.getText().toString().trim());
            setResult(RESULT_OK, new Intent().putExtra("modify_name", etName.getText().toString().trim()));
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultUploadHead(UploadHeadBean data) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
