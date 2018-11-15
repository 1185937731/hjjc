package com.ccnu.hjjc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.google.gson.Gson;

import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private HttpLoader httpLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button)this.findViewById(R.id.button);
        httpLoader=new HttpLoader();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                login("admin","12");
//                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });
    }

    //网络通信，登陆
    public void login(String name,String password){
        httpLoader.login(name,password).subscribe(new Action1<LoginReturnObject>() {
            @Override
            public void call(LoginReturnObject loginReturnObject) {
                System.out.println("数据"+new Gson().toJson(loginReturnObject));
                //获取成功，数据在loginReturnObject
                Toast.makeText(LoginActivity.this,"获取数据"+loginReturnObject.getLogin()+"&"+loginReturnObject.getMonitor(),
                        Toast.LENGTH_LONG).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //获取失败
                Log.e("TAG","error message:"+throwable.getMessage());
                if(throwable instanceof Fault){
                    Fault fault = (Fault) throwable;
                    if(fault.getErrorCode() == 404){
                        //错误处理
                    }else if(fault.getErrorCode() == 500){
                        //错误处理
                    }else if(fault.getErrorCode() == 501){
                        //错误处理
                    }
                }
            }
        });
    }
}
