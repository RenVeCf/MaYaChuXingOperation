package com.ipd.mayachuxingoperation.common.config;

/**
 * Description ：URL 配置
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface UrlConfig {
    /**
     * 域名
     */
    String BASE_URL = "http://zl.v-lionsafety.com/index/";
    String BASE_LOCAL_URL = "http://zl.v-lionsafety.com/";

    /**
     * 登陆
     */
    String LOGIN = "Repair/RepairLogin"; //点击登陆
    String CAPTCHA = "Register/phoneCode"; //接收验证码


    /**
     * 首页
     */
    String USER_INFO = "Repair/personalData"; //个人资料
    String HOME_INFO = "Repair/repairHome"; //运营端首页
    String UPLOAD_HEAD = "Repair/getRepairUrl"; //头像上传
    String MODIFY_NAME = "Repair/editName"; //修改姓名
    String DAY_MALFUNCTION = "Problem/getTodayProblem"; //今日故障
    String PAUSE = "Problem/setStop"; //暂停使用
    String MODIFY = "Problem/setNormal"; //确认修复
    String UPLOAD_IMG = "Problem/uploadFile"; //图片上传


    /**
     * 故障|停用|修复   车辆列表
     */
    String MALFUNCTION_SUM = "Problem/getProblems"; //故障|停用|修复   车辆列表
    String MALFUNCTION_DETAILS = "Problem/getBikeBadeDetail"; //故障详情
    String FEED_LIST = "repair/feedBikes"; //馈电车辆列表
    String IS_FEED = "repair/feedBike"; //将馈电车修改为不亏电


    /**
     * 工作统计
     */
    String WORK_SUM = "Repair/workStatistics"; //工作统计
    String CONTROL_LOCK = "repair/lock"; //开锁、关锁
    String ELECTRIC_BOX = "repair/battery"; //电池仓开关锁
}
