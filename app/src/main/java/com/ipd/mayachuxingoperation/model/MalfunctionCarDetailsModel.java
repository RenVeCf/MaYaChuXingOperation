package com.ipd.mayachuxingoperation.model;

import android.content.Context;

import com.ipd.mayachuxingoperation.api.Api;
import com.ipd.mayachuxingoperation.base.BaseModel;
import com.ipd.mayachuxingoperation.progress.ObserverResponseListener;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class MalfunctionCarDetailsModel<T> extends BaseModel {

    public void getMalfunctionCarDetails(Context context, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                                         ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().login(map), observerListener);
        paramSubscribe(context, Api.getApiService().getMalfunctionCarDetails(map), observerListener, transformer, isDialog, cancelable);
    }
    //// TODO: 2017/12/27 其他需要请求、数据库等等的操作
}
