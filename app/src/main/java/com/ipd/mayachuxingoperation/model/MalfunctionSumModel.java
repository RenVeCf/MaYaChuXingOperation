package com.ipd.mayachuxingoperation.model;

import android.content.Context;

import com.ipd.mayachuxingoperation.api.Api;
import com.ipd.mayachuxingoperation.base.BaseModel;
import com.ipd.mayachuxingoperation.progress.ObserverResponseListener;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class MalfunctionSumModel<T> extends BaseModel {

    public void getMalfunctionSum(Context context, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                                  ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().login(map), observerListener);
        paramSubscribe(context, Api.getApiService().getMalfunctionSum(map), observerListener, transformer, isDialog, cancelable);
    }

    public void getPause(Context context, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                         ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().Pause(map), observerListener);
        paramSubscribe(context, Api.getApiService().getPause(map), observerListener, transformer, isDialog, cancelable);
    }

    public void getModify(Context context, String id, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                          ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().Pause(map), observerListener);
        paramSubscribe(context, Api.getApiService().getModify(id, map), observerListener, transformer, isDialog, cancelable);
    }

    public void getUploadImg(Context context, TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable,
                             ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().login(map), observerListener);
        paramSubscribe(context, Api.getApiService().getUploadImg(map), observerListener, transformer, isDialog, cancelable);
    }
    //// TODO: 2017/12/27 其他需要请求、数据库等等的操作
}
