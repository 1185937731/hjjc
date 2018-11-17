package com.ccnu.hjjc.http;

import com.ccnu.hjjc.Bean.GetDetialNodes;
import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.Node;
import  com.ccnu.hjjc.Bean.GetNodesInfo;
import rx.Observable;


public class HttpLoader extends ObjectLoader {
    private ApiService apiService;

    public HttpLoader(){
        apiService = RetrofitServiceManager.getInstance().create(ApiService.class);
    }


    //登陆
    public Observable<LoginReturnObject> login(String name, String password){
        return observe(apiService.Login(name,password));
    }

    //主页请求数据

    public Observable<GetNodesInfo> nodesInfo(String name){
        return observe(apiService.NodesInfo(name));
    }
    //详细房间信息
    public Observable<GetDetialNodes> detailNodesInfo(String name, String floorId , String  roomId){
        return observe(apiService.DetialNodesInfo(name,floorId,roomId));
    }


}
