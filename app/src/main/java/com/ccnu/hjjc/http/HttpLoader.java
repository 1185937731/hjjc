package com.ccnu.hjjc.http;
import com.ccnu.hjjc.Bean.LoginReturnObject;

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



}
