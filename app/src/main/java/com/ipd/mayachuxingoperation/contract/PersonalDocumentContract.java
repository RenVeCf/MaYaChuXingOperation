package com.ipd.mayachuxingoperation.contract;

import com.ipd.mayachuxingoperation.base.BasePresenter;
import com.ipd.mayachuxingoperation.base.BaseView;
import com.ipd.mayachuxingoperation.bean.ModifyNameBean;
import com.ipd.mayachuxingoperation.bean.UploadHeadBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface PersonalDocumentContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultModifyName(ModifyNameBean data);

        void resultUploadHead(UploadHeadBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void getModifyName(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getUploadHead(TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable);
    }
}