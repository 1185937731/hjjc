package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ccnu.hjjc.Bean.LoginReturnObject;
import com.ccnu.hjjc.Bean.RegistReturnObject;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.google.gson.Gson;

import rx.functions.Action1;

public class RegisterActivity extends AppCompatActivity{
    private EditText et_username;
    private EditText et_pwd;
    private EditText et_pwd_again;
    private EditText et_phone;
    private EditText et_email;
    private Button reg_ok;
    private HttpLoader httpLoader;
    public int regist_get;

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
                httpLoader = new HttpLoader();
                String username=et_username.getText().toString();
                String password=et_pwd.getText().toString();
                String password2=et_pwd_again.getText().toString();
                String phone=et_phone.getText().toString();
                String email=et_email.getText().toString();
                if(password!=password2){
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致",
                            Toast.LENGTH_LONG).show();
                }else{
                    regist(username,password,phone,email);

                }


            }
        });

    }

    public void regist(String name, String password,String phone,String email) {
        httpLoader.regist(name, password,phone,email).subscribe(new Action1<RegistReturnObject>() {
            @Override
            public void call(RegistReturnObject registReturnObject) {
                System.out.println("数据" + new Gson().toJson(registReturnObject));
                //获取成功，数据在registReturnObject
                regist_get=registReturnObject.getRegist();
                if(regist_get==1) {
                    System.out.println("注册成功");
                    Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }else if(regist_get==2){
                    Toast.makeText(RegisterActivity.this, "注册失败—服务器操作失败", Toast.LENGTH_SHORT).show();
                }else if(regist_get==3){
                    Toast.makeText(RegisterActivity.this, "注册失败—该用户名已存在", Toast.LENGTH_SHORT).show();
                }else if(regist_get==4){
                    Toast.makeText(RegisterActivity.this, "注册失败—该区域编号已存在", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(RegisterActivity.this, "获取数据" + registReturnObject.getRegist(),
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
