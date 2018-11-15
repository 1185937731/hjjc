package com.ccnu.hjjc.http;


import com.ccnu.hjjc.Bean.LoginReturnObject;
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

    //普通用户注册
    @FormUrlEncoded
    @POST("MonitorRegist/")
    Observable<RegistReturnObject> Register(@Field("r_name") String names,
                                            @Field("r_pwd") String password,
                                            @Field("r_tel") String phone,
                                            @Field("r_email") String email);



}
