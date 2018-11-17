package com.ccnu.hjjc.http;

import com.ccnu.hjjc.Bean.GetDetialNodes;

import com.ccnu.hjjc.Bean.GetClientsName;
import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.Node;
import com.ccnu.hjjc.Bean.GetNodesInfo;
import com.ccnu.hjjc.Bean.InformReturnObject;

import com.ccnu.hjjc.Bean.RegistReturnObject;
import com.ccnu.hjjc.Bean.RoomGetReturnObject;

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
    //请求主页所有数据
    @FormUrlEncoded
    @POST("Android_nodes_info/")
    Observable<GetNodesInfo> NodesInfo(@Field("name") String name);

    //详细页面信息
    @FormUrlEncoded
    @POST("check_nodes_info/")
    Observable<GetDetialNodes>DetialNodesInfo(@Field("username") String username,
                                              @Field("floor_id") String floorId,
                                              @Field("room_id") String roomId);

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
    //APP提交房间号
    @FormUrlEncoded
    @POST("app_set_threshold")
    Observable<RoomGetReturnObject> roomGet(@Field("username") String username,
                                            @Field("floor_id") String floor_id,
                                            @Field("room_id") String room_id);


    //配置到该房间
    @FormUrlEncoded
    @POST("app_set_threshold")
    Observable<String> theshold(@Field("username")String username,
                                @Field("floor_id") String floor_id,
                                @Field("room_id") String room_id,
                                 @Field("humiMax") int humiMax,
                                 @Field("humiMin") int humiMin,
                                 @Field("tempMin") int tempMin,
                                 @Field("tempMax") int tempMax);

    //配置到所有房间
    @FormUrlEncoded
    @POST("app_set_threshold")
    Observable<String> thesholdall(@Field("usr_name")String username,
                                 @Field("ifALL") int ifall,
                                 @Field("humiMax") int humiMax,
                                 @Field("humiMin") int humiMin,
                                 @Field("tempMin") int tempMin,
                                 @Field("tempMax") int tempMax);

    //管理用户注册
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
