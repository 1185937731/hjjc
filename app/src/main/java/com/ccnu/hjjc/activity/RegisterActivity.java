package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ccnu.hjjc.R;

public class RegisterActivity extends AppCompatActivity{
    private EditText et_username;
    private EditText et_pwd;
    private EditText et_pwd_again;
    private EditText et_phone;
    private EditText et_email;
    private Button reg_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState){
//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置禁止横屏
        initView();

    }

    private void initView(){

        et_username=(EditText) findViewById(R.id.et_username);
        et_pwd=(EditText) findViewById(R.id.et_pwd);
        et_pwd_again=(EditText) findViewById(R.id.et_pwd_again);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_email=(EditText)findViewById(R.id.et_email);
        reg_ok=(Button)findViewById(R.id.reg_ok);

        //注册
        reg_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }



}
