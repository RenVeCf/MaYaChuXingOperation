package com.ipd.mayachuxingoperation.presenter;

import android.content.Context;

import com.ipd.mayachuxingoperation.bean.FeedListBean;
import com.ipd.mayachuxingoperation.bean.IsFeedBean;
import com.ipd.mayachuxingoperation.contract.FeedContract;
import com.ipd.mayachuxingoperation.model.FeedModel;
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
public class FeedPresenter extends FeedContract.Presenter {

    private FeedModel model;
    private Context context;

    public FeedPresenter(Context context) {
        this.model = new FeedModel();
        this.context = context;
    }

    @Override
    public void getFeedList(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getFeedList(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultFeedList((FeedListBean) o);
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
    public void getIsFeed(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getIsFeed(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultIsFeed((IsFeedBean) o);
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