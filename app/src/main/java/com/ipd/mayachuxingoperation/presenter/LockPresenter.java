package com.ipd.mayachuxingoperation.presenter;

import android.content.Context;

import com.ipd.mayachuxingoperation.bean.ControlLockBean;
import com.ipd.mayachuxingoperation.bean.ElectricBoxBean;
import com.ipd.mayachuxingoperation.contract.LockContract;
import com.ipd.mayachuxingoperation.model.LockModel;
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
public class LockPresenter extends LockContract.Presenter {

    private LockModel model;
    private Context context;

    public LockPresenter(Context context) {
        this.model = new LockModel();
        this.context = context;
    }

    @Override
    public void getControlLock(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getControlLock(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultControlLock((ControlLockBean) o);
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
    public void getElectricBox(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getElectricBox(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultElectricBox((ElectricBoxBean) o);
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