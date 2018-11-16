package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ccnu.hjjc.Bean.GetClientsName;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.google.gson.Gson;

import java.util.List;

import rx.functions.Action1;

public class AdminGetClientActivity extends AppCompatActivity {

    private ListView clients;
    private ImageView addClient;
    private HttpLoader httpLoader;
    private ArrayAdapter<String> adapter;
    private List<String> names;
    private LinearLayout cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_get_client);
        clients=findViewById(R.id.lv_clients);
        addClient=findViewById(R.id.iv_add_client);
        cancel = findViewById(R.id.ll_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminGetClientActivity.this, MainActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminGetClientActivity.this,AddClientActivity.class);
                startActivity(intent);
                finish();
            }
        });
        httpLoader=new HttpLoader();
        showAllUsr ("admin");
    }

    public void showAllUsr (String username){
        httpLoader.showAllUsr(username).subscribe(new Action1<GetClientsName>() {
            @Override
            public void call(GetClientsName datas) {
                System.out.println("数据Admin"+new Gson().toJson(datas));
                names=datas.getUser();
                adapter=new ArrayAdapter<String>(AdminGetClientActivity.this, android.R.layout.simple_list_item_1,names);
                clients.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //获取失败
                Log.e("TAG", "error message:" + throwable.getMessage());
                System.out.println("异常Admin"+throwable.getMessage());
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
