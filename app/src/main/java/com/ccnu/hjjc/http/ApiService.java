package com.ccnu.hjjc.http;


import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.InformReturnObject;

import com.ccnu.hjjc.Bean.RegistReturnObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface ApiService {

    //登陆
    @FormUrlEncoded
    @POST("AndroidLoginJudge/")
    Observable<LoginReturnObject> Login(@Field("name") String name,
                                        @Field("password") String password);

    //用户注册
    @FormUrlEncoded
    @POST("MonitorRegist/")
    Observable<RegistReturnObject> AdminRegister(
            @Field("area") String area,
            @Field("area_name") String area_name,
            @Field("r_code")String code,
            @Field("r_name") String names,
            @Field("r_pwd") String password,
            @Field("r_tel") String phone,
            @Field("r_email") String email);
    //用户注册
    @FormUrlEncoded
    @POST("MonitorRegist/")
    Observable<RegistReturnObject> Register(
            @Field("r_name") String names,
            @Field("r_pwd") String password,
            @Field("r_tel") String phone,
            @Field("r_email") String email);




    //读取电话、邮箱
    @FormUrlEncoded
    @POST("AndroidModifyUserInfo/")
    Observable<InformReturnObject> Infrom(@Field("username") String name);

    //修改电话
    @FormUrlEncoded
    @POST("AndroidModifyUserInfo/")
    Observable<String> ChangePhone(@Field("username") String name,
                                         @Field("new_tel") String new_tel);
    //修改邮箱
    @FormUrlEncoded
    @POST("AndroidModifyUserInfo/")
    Observable<String> ChangeMail(@Field("username") String name,
                                   @Field("new_email") String new_email);
    //修改密码
    @FormUrlEncoded
    @POST("AndroidModifyUserInfo/")
    Observable<String> ChangePassword(@Field("username") String name,
                                  @Field("old_pwd") String old_pwd,
                                      @Field("new_pwd") String new_pwd);
}
