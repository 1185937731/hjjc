package com.ccnu.hjjc.http;

import com.ccnu.hjjc.Bean.GetClientsName;
import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.RegistReturnObject;

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

    //普通用户注册
    public Observable<RegistReturnObject> regist(String name, String password,String phone,String email){
        return observe(apiService.Register(name,password,phone,email));
    }
    //管理员注册
    public Observable<RegistReturnObject> adminregist(String area,String area_name,String code,String name, String password,String phone,String email){
        return observe(apiService.AdminRegister(area,area_name,code,name,password,phone,email));
    }
    //登陆
    public Observable<GetClientsName> showAllUsr(String username){
        return observe(apiService.showAllUsr(username));
    }

    //新增用户
    public Observable<String> addClient(String username,String newClientName,int monitor){
        return observe(apiService.addClient(username,newClientName,monitor));
    }

    //节点配置
    public Observable<String> addNodeInfo(String username, String dev_eui,String floor_id, String room_id,
                                          String type, int humiMax, int humiMin, int tempMin, int tempMax){
        return observe(apiService.addNodeInfo(username, dev_eui, floor_id,  room_id,
                 type,  humiMax,  humiMin,  tempMin,  tempMax));
    }


}
