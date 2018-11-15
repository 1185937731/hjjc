package com.ccnu.hjjc.http;
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


}
