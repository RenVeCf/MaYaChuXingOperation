package com.ipd.mayachuxingoperation.api;

import com.ipd.mayachuxingoperation.bean.CaptchaBean;
import com.ipd.mayachuxingoperation.bean.ControlLockBean;
import com.ipd.mayachuxingoperation.bean.DayMalfunctionBean;
import com.ipd.mayachuxingoperation.bean.ElectricBoxBean;
import com.ipd.mayachuxingoperation.bean.FeedListBean;
import com.ipd.mayachuxingoperation.bean.HomeInfoBean;
import com.ipd.mayachuxingoperation.bean.IsFeedBean;
import com.ipd.mayachuxingoperation.bean.LoginBean;
import com.ipd.mayachuxingoperation.bean.MalfunctionCarDetailsBean;
import com.ipd.mayachuxingoperation.bean.MalfunctionSumBean;
import com.ipd.mayachuxingoperation.bean.ModifyBean;
import com.ipd.mayachuxingoperation.bean.ModifyNameBean;
import com.ipd.mayachuxingoperation.bean.PauseBean;
import com.ipd.mayachuxingoperation.bean.SumWorkBean;
import com.ipd.mayachuxingoperation.bean.UploadHeadBean;
import com.ipd.mayachuxingoperation.bean.UploadImgBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.ipd.mayachuxingoperation.common.config.UrlConfig.CAPTCHA;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.CONTROL_LOCK;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.DAY_MALFUNCTION;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.ELECTRIC_BOX;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.FEED_LIST;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.HOME_INFO;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.IS_FEED;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.LOGIN;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.MALFUNCTION_DETAILS;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.MALFUNCTION_SUM;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.MODIFY;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.MODIFY_NAME;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.PAUSE;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.UPLOAD_HEAD;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.UPLOAD_IMG;
import static com.ipd.mayachuxingoperation.common.config.UrlConfig.WORK_SUM;

/**
 * Description ：请求配置
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */
public interface ApiService {

    //接收验证码
    @GET(CAPTCHA)
    Observable<CaptchaBean> getCaptcha(@QueryMap Map<String, String> map);

    //用户登录
    @FormUrlEncoded
    @POST(LOGIN)
    Observable<LoginBean> getLogin(@FieldMap Map<String, String> map);

    //运营端首页
    @POST(HOME_INFO)
    Observable<HomeInfoBean> getHomeInfo();

    //今日故障
    @FormUrlEncoded
    @POST(DAY_MALFUNCTION)
    Observable<DayMalfunctionBean> getDayMalfunction(@FieldMap Map<String, String> map);

    //暂停使用
    @GET(PAUSE)
    Observable<PauseBean> getPause(@QueryMap Map<String, String> map);

    //故障详情
    @GET(MALFUNCTION_DETAILS)
    Observable<MalfunctionCarDetailsBean> getMalfunctionCarDetails(@QueryMap Map<String, String> map);

    //确认修复
    @FormUrlEncoded
    @POST(MODIFY)
    Observable<ModifyBean> getModify(@Query("id") String id, @FieldMap Map<String, String> map);

    //图片上传
    @Multipart
    @POST(UPLOAD_IMG)
    Observable<UploadImgBean> getUploadImg(@PartMap Map<String, RequestBody> map);

    //头像上传
    @Multipart
    @POST(UPLOAD_HEAD)
    Observable<UploadHeadBean> getUploadHead(@PartMap Map<String, RequestBody> map);

    //修改姓名
    @FormUrlEncoded
    @POST(MODIFY_NAME)
    Observable<ModifyNameBean> getModifyName(@FieldMap Map<String, String> map);

    //故障|停用|修复   车辆列表
    @GET(MALFUNCTION_SUM)
    Observable<MalfunctionSumBean> getMalfunctionSum(@QueryMap Map<String, String> map);

    //工作统计
    @GET(WORK_SUM)
    Observable<SumWorkBean> getSumWork(@QueryMap Map<String, String> map);

    //开锁、关锁
    @FormUrlEncoded
    @POST(CONTROL_LOCK)
    Observable<ControlLockBean> getControlLock(@FieldMap Map<String, String> map);

    //电池仓开关锁
    @FormUrlEncoded
    @POST(ELECTRIC_BOX)
    Observable<ElectricBoxBean> getElectricBox(@FieldMap Map<String, String> map);

    //馈电车辆列表
    @GET(FEED_LIST)
    Observable<FeedListBean> getFeedList(@QueryMap Map<String, String> map);

    //将馈电车修改为不亏电
    @GET(IS_FEED)
    Observable<IsFeedBean> getIsFeed(@QueryMap Map<String, String> map);
}
