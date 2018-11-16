package com.ccnu.hjjc.http;
import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.InformReturnObject;


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

    //读取电话、邮箱
    public Observable<InformReturnObject> infrom(String name){
        return observe(apiService.Infrom(name));
    }

    //修改信息
    public Observable<String> change_phone(String name, String new_tel){
        return observe(apiService.ChangePhone(name,new_tel));
    }

    //修改邮箱
    public Observable<String> change_email(String name, String new_email){
        return observe(apiService.ChangeMail(name,new_email));
    }
    //修改密码
    public Observable<String> change_pwd(String name, String old_pwd,String new_pwd){
        return observe(apiService.ChangePassword(name,old_pwd,new_pwd));
    }

}
