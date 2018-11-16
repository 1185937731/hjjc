package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;

import rx.functions.Action1;

public class AddClientActivity extends AppCompatActivity {

    private LinearLayout cancel;
    private EditText clientname;
    private RadioGroup monitor;
    private Button addClient;
    private int monitorValue = 1;//‘0’为普通用户，‘1’为管理员；
    private HttpLoader httpLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        httpLoader = new HttpLoader();
        initView();
    }


    public void initView() {
        addClient = findViewById(R.id.bt_add_client);
        cancel = findViewById(R.id.ll_cancel);
        clientname = findViewById(R.id.et_clientname);
        monitor = findViewById(R.id.rg_monitor);
        addClient = findViewById(R.id.bt_add_client);
        addClient.setEnabled(false);
        addClient.setClickable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddClientActivity.this, AdminGetClientActivity.class);
                startActivity(intent);
                finish();
            }
        });
        clientname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!clientname.getText().toString().isEmpty()) {
                    addClient.setEnabled(true);
                    addClient.setClickable(true);
//                    addClient.setBackgroundColor(getResources().getColor(R.color.btn_basic_degree_bg));
                }
            }
        });
        monitor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_manager) {
                    monitorValue = 1;
                } else if (i == R.id.rb_client) {
                    monitorValue = 0;
                }
            }
        });
        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clientName = clientname.getText().toString().trim();
                if (!clientName.isEmpty()) {
                    System.out.println("用户名"+clientName);
                    System.out.println("管理员"+monitorValue);
                    addClient("admin", clientName, monitorValue);
                } else {
                    Toast.makeText(AddClientActivity.this, "用户名不能为空", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void addClient(String username, String newClientName, int monitor) {
        httpLoader.addClient(username, newClientName, monitor).subscribe(new Action1<String>() {
            @Override
            public void call(String string) {
                System.out.println("数据addClient" + string);
                if (string.equals("0")) {
                    Toast.makeText(AddClientActivity.this, "该用户名已存在", Toast.LENGTH_LONG).show();
                } else if (string.equals("1")) {
                    Toast.makeText(AddClientActivity.this, "新增失败", Toast.LENGTH_LONG).show();
                } else if (string.equals("2")) {
                    Toast.makeText(AddClientActivity.this, "新增成功", Toast.LENGTH_LONG).show();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //获取失败
                Log.e("TAG", "error message:" + throwable.getMessage());
                System.out.println("异常Admin" + throwable.getMessage());
                Toast.makeText(AddClientActivity.this, "请求失败", Toast.LENGTH_LONG).show();
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
