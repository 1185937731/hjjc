package com.ccnu.hjjc.http;

import com.ccnu.hjjc.Bean.GetDetialNodes;
import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.Node;
import com.ccnu.hjjc.Bean.GetNodesInfo;

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
}
