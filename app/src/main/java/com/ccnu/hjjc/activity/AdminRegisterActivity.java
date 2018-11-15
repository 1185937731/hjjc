package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ccnu.hjjc.R;

public class AdminRegisterActivity extends AppCompatActivity {
    private EditText et_area_id;
    private EditText et_area_name;
    private EditText et_username;
    private EditText et_reg_code;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminregister);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置禁止横屏
        initView();

    }

    private void initView(){

        et_username=(EditText) findViewById(R.id.et_username);
        et_area_id=(EditText) findViewById(R.id.et_area_id);
        et_area_name=(EditText) findViewById(R.id.et_area_name);
        et_reg_code=(EditText)findViewById(R.id.et_reg_code);
        btn_next=(Button)findViewById(R.id.btn_next);

        //注册
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminRegisterActivity.this, AdminRegisterActivity2.class);
                startActivity(intent);


            }
        });

    }

}
