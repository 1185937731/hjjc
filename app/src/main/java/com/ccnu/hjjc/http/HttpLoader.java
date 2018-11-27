package com.ccnu.hjjc.http;

import com.ccnu.hjjc.Bean.GetDetialNodes;

import com.ccnu.hjjc.Bean.GetClientsName;
import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.Node;
import  com.ccnu.hjjc.Bean.GetNodesInfo;
import com.ccnu.hjjc.Bean.InformReturnObject;

import com.ccnu.hjjc.Bean.RegistReturnObject;
import com.ccnu.hjjc.Bean.RoomGetReturnObject;

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
    //登陆
    public Observable<GetClientsName> showAllUsr(String username){
        return observe(apiService.showAllUsr(username));
    }

    //新增用户
    public Observable<String> addClient(String username,String newClientName,int monitor){
        return observe(apiService.addClient(username,newClientName,monitor));
    }

    //温湿度节点配置
    public Observable<String> addNodeInfo(String username, String dev_eui,String floor_id, String room_id,
                                          String type, int humiMax, int humiMin, int tempMin, int tempMax){
        return observe(apiService.addNodeInfo(username, dev_eui, floor_id,  room_id,
                 type,  humiMax,  humiMin,  tempMin,  tempMax));
    }


    //其他节点配置
    public Observable<String> addOtherNodeInfo(String username, String dev_eui,String floor_id, String room_id,
                                          String type){
        return observe(apiService.addOtherNodeInfo(username, dev_eui, floor_id,  room_id, type));
    }

    //提交房间
    public Observable<RoomGetReturnObject> roomGet(String username, String floor_id, String room_id){
        return observe(apiService.roomGet(username, floor_id,room_id));
    }

    //配置该房间
    public Observable<String> theshold(String username, String floor_id, String room_id,
                                       int humiMax, int humiMin, int tempMin, int tempMax){
        return observe(apiService.theshold(username,  floor_id,  room_id,
                  humiMax,  humiMin,  tempMin,  tempMax));
    }

    //配置所有房间
    public Observable<String> thesholdall(String username, int humiMax, int humiMin, int tempMin, int tempMax){
        return observe(apiService.thesholdall(username, 1,
                humiMax,  humiMin,  tempMin,  tempMax));
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
