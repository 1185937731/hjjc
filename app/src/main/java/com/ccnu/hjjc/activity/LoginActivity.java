package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ccnu.hjjc.R;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username, et_pwd;

    /*** 登录按钮*/

    private Button btn_login;
    private TextView tv_admin_register, tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置禁止横屏
        initView();
    }

    private void initView() {

        // setTitle(getString(R.string.btn_login));

//        setTitle("登陆");

        et_username = (EditText) this.findViewById(R.id.et_username);
        et_pwd = (EditText) this.findViewById(R.id.et_pwd);

        //登录

        btn_login = (Button) this.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                doLogin();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }
        });

        //管理员注册

        tv_admin_register = (TextView) this.findViewById(R.id.tv_admin_register);
        tv_admin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, AdminRegisterActivity.class);
                startActivity(intent);


            }
        });

        //普通用户注册

        tv_register = (TextView) this.findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }



}
