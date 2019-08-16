package com.ipd.mayachuxingoperation.contract;

import com.ipd.mayachuxingoperation.base.BasePresenter;
import com.ipd.mayachuxingoperation.base.BaseView;
import com.ipd.mayachuxingoperation.bean.DayMalfunctionBean;
import com.ipd.mayachuxingoperation.bean.HomeInfoBean;
import com.ipd.mayachuxingoperation.bean.ModifyBean;
import com.ipd.mayachuxingoperation.bean.PauseBean;
import com.ipd.mayachuxingoperation.bean.UploadImgBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface HomeContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultHomeInfo(HomeInfoBean data);

        void resultDayMalfunction(DayMalfunctionBean data);

        void resultPause(PauseBean data);

        void resultModify(ModifyBean data);

        void resultUploadImg(UploadImgBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getHomeInfo(boolean isDialog, boolean cancelable);

        public abstract void getDayMalfunction(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getPause(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getModify(String id, TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getUploadImg(TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable);
    }
}