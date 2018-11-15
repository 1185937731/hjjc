package com.ccnu.hjjc.activity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;


import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.MyApplication;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.google.gson.Gson;

import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username, et_pwd;
    private Button btn_login;
    private TextView tv_admin_register, tv_register;
    private HttpLoader httpLoader;
    public int login_get;
    public int monitor_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置禁止横屏
        initView();
    }

    private void initView() {

        et_username = (EditText) this.findViewById(R.id.et_username);
        et_pwd = (EditText) this.findViewById(R.id.et_pwd);
        tv_admin_register = (TextView) this.findViewById(R.id.tv_admin_register);
        tv_register = (TextView) this.findViewById(R.id.tv_register);
        btn_login = (Button) this.findViewById(R.id.btn_login);

        //登录
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpLoader = new HttpLoader();
                String username=et_username.getText().toString();
                String password=et_pwd.getText().toString();
                login(username,password);
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);


            }
        });

        //管理员注册

        tv_admin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AdminRegisterActivity.class);
                startActivity(intent);

            }
        });

        //普通用户注册
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    //网络通信，登陆
    public void login(String name, String password) {
        httpLoader.login(name, password).subscribe(new Action1<LoginReturnObject>() {
            @Override
            public void call(LoginReturnObject loginReturnObject) {
                System.out.println("数据" + new Gson().toJson(loginReturnObject));
                //获取成功，数据在loginReturnObject
                login_get=loginReturnObject.getLogin();
                monitor_get=loginReturnObject.getMonitor();
                if(login_get==0) {
                    Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                }else if(login_get==1){
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }else if(login_get==2){
                    System.out.println("登录成功");
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("username_data",et_username.getText().toString());
                    startActivity(intent);
                }


                Toast.makeText(LoginActivity.this, "获取数据" + loginReturnObject.getLogin() + "&" + loginReturnObject.getMonitor(),
                        Toast.LENGTH_LONG).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //获取失败
                Log.e("TAG", "error message:" + throwable.getMessage());
                if (throwable instanceof Fault) {
                    Fault fault = (Fault) throwable;
                    if (fault.getErrorCode() == 404) {
                        //错误处理
                    } else if (fault.getErrorCode() == 500) {
                        //错误处理
                    } else if (fault.getErrorCode() == 501) {
                        //错误处理
                    }
                }

            }
        });
    }


}