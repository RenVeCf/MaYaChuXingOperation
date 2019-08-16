package com.ipd.mayachuxingoperation.progress;

import com.ipd.mayachuxingoperation.utils.ExceptionHandle;

/**
 * 请求监听
 */

public interface ObserverResponseListener<T> {
    /**
     * 响应成功
     * @param t
     */
    void onNext(T t);

    /**
     * 响应失败
     * @param e
     */
    void onError(ExceptionHandle.ResponeThrowable e);
}
