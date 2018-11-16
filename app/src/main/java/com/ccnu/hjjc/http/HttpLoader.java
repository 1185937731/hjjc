package com.ccnu.hjjc.http;
import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.InformReturnObject;

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

    //读取电话、邮箱
    public Observable<InformReturnObject> infrom(String name){
        return observe(apiService.Infrom(name));
    }
    //普通用户注册
    public Observable<RegistReturnObject> regist(String name, String password,String phone,String email){
        return observe(apiService.Register(name,password,phone,email));
    }
    //管理员注册
    public Observable<RegistReturnObject> adminregist(String area,String area_name,String code,String name, String password,String phone,String email){
        return observe(apiService.AdminRegister(area,area_name,code,name,password,phone,email));
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
