package com.ipd.mayachuxingoperation.contract;

import com.ipd.mayachuxingoperation.base.BasePresenter;
import com.ipd.mayachuxingoperation.base.BaseView;
import com.ipd.mayachuxingoperation.bean.ControlLockBean;
import com.ipd.mayachuxingoperation.bean.ElectricBoxBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface LockContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultControlLock(ControlLockBean data);

        void resultElectricBox(ElectricBoxBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getControlLock(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getElectricBox(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}