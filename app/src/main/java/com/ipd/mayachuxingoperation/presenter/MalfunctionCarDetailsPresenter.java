package com.ipd.mayachuxingoperation.presenter;

import android.content.Context;

import com.ipd.mayachuxingoperation.bean.MalfunctionCarDetailsBean;
import com.ipd.mayachuxingoperation.contract.MalfunctionCarDetailsContract;
import com.ipd.mayachuxingoperation.model.MalfunctionCarDetailsModel;
import com.ipd.mayachuxingoperation.progress.ObserverResponseListener;
import com.ipd.mayachuxingoperation.utils.ExceptionHandle;
import com.ipd.mayachuxingoperation.utils.ToastUtil;

import java.util.TreeMap;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class MalfunctionCarDetailsPresenter extends MalfunctionCarDetailsContract.Presenter {

    private MalfunctionCarDetailsModel model;
    private Context context;

    public MalfunctionCarDetailsPresenter(Context context) {
        this.model = new MalfunctionCarDetailsModel();
        this.context = context;
    }

    @Override
    public void getMalfunctionCarDetails(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getMalfunctionCarDetails(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultMalfunctionCarDetails((MalfunctionCarDetailsBean) o);
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