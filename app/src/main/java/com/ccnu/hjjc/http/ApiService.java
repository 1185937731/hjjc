package com.ccnu.hjjc.http;


import com.ccnu.hjjc.Bean.GetClientsName;
import com.ccnu.hjjc.Bean.LoginReturnObject;

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

    //查看所有用户
    @FormUrlEncoded
    @POST("show_all_usr/")
    Observable<GetClientsName> showAllUsr(@Field("username") String username);

    //新增用户
    @FormUrlEncoded
    @POST("new_user/")
    Observable<String> addClient(@Field("username") String username,
                                 @Field("new_usr") String newClient,
                                         @Field("new_monitor") int monitor);
    //注册节点
    @FormUrlEncoded
    @POST("app_set_nodeInfo/")
    Observable<String> addNodeInfo(@Field("username") String username,
                                 @Field("dev_eui") String dev_eui,
                                 @Field("floor_id") String floor_id,
                                   @Field("room_id") String room_id,
                                   @Field("type") String type,
//                                   @Field("posx") int posx,
//                                   @Field("posy") int posy,
                                   @Field("humiMax") int humiMax,
                                   @Field("humiMin") int humiMin,
                                   @Field("tempMin") int tempMin,
                                   @Field("tempMax") int tempMax);
}
