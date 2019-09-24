package com.ipd.mayachuxingoperation.contract;

import com.ipd.mayachuxingoperation.base.BasePresenter;
import com.ipd.mayachuxingoperation.base.BaseView;
import com.ipd.mayachuxingoperation.bean.FeedListBean;
import com.ipd.mayachuxingoperation.bean.IsFeedBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface FeedContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultFeedList(FeedListBean data);

        void resultIsFeed(IsFeedBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getFeedList(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getIsFeed(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}