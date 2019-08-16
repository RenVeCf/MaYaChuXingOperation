package com.ipd.mayachuxingoperation.presenter;

import android.content.Context;

import com.ipd.mayachuxingoperation.bean.ModifyNameBean;
import com.ipd.mayachuxingoperation.bean.UploadHeadBean;
import com.ipd.mayachuxingoperation.contract.PersonalDocumentContract;
import com.ipd.mayachuxingoperation.model.PersonalDocumentModel;
import com.ipd.mayachuxingoperation.progress.ObserverResponseListener;
import com.ipd.mayachuxingoperation.utils.ExceptionHandle;
import com.ipd.mayachuxingoperation.utils.ToastUtil;

import java.util.TreeMap;

import okhttp3.RequestBody;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class PersonalDocumentPresenter extends PersonalDocumentContract.Presenter {

    private PersonalDocumentModel model;
    private Context context;

    public PersonalDocumentPresenter(Context context) {
        this.model = new PersonalDocumentModel();
        this.context = context;
    }

    @Override
    public void getModifyName(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getModifyName(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultModifyName((ModifyNameBean) o);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    ToastUtil.showShortToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

    @Override
    public void getUploadHead(TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable) {
        model.getUploadHead(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultUploadHead((UploadHeadBean) o);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    ToastUtil.showShortToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }
}