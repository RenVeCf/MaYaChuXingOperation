package com.ipd.mayachuxingoperation.common.config;

/**
 * Description ：公共配置类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface IConstants {
    /**
     * 包名
     */
    String PACKAGE_NAME = "com.ipd.mayachuxingoperation";

    /**
     * SharedPreferences
     * 共享参数
     */
    String FIRST_APP = "is_first"; //第一次进应用
    String IS_LOGIN = "is_login"; //已经登录
    String TOKEN = "is_token"; //token
    String NAME = "name"; //用户真实姓名
    String PHONE = "phone"; //用户手机号码
    String AVATAR = "avatar"; //头像
    String SEX = "sex"; //性别
    String AGE = "age"; //年龄
    String CITY = "city"; //城市
    String HOW_PAGE = "how_page"; //未登录时点击了首页的哪个Fragment，登录后就跳到哪个Fragment


    /**
     * requestCode
     * 请求码
     */
    int REQUEST_CODE_90 = 90;//修改个人资料的回跳
    int REQUEST_CODE_91 = 91;//修改姓名的回跳
    int REQUEST_CODE_92 = 92;//
    int REQUEST_CODE_93 = 93;//
    int REQUEST_CODE_94 = 94;//
    int REQUEST_CODE_95 = 95;//
    int REQUEST_CODE_96 = 96;//
    int REQUEST_CODE_97 = 97;//
    int REQUEST_CODE_98 = 98;//
    int REQUEST_CODE_99 = 99;//
    int REQUEST_CODE_100 = 100;
    int REQUEST_CODE_101 = 101;
    int REQUEST_CODE_102 = 102;
    int REQUEST_CODE_103 = 103;
    int REQUEST_CODE_104 = 104;
    int REQUEST_CODE_105 = 105;

    /**
     * resultCode
     * 返回码
     */
    int RESULT_CODE = 0;
}
