package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ccnu.hjjc.Bean.GetClientsName;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class NodeRegistActivity extends AppCompatActivity {

    private Spinner type_choice;
    private String[] typeChoice={"tem_hum","smoke","air","door","water"};
    private String nodeType=typeChoice[0];//节点类型，默认
    private EditText node_num;
    private EditText floor_id;
    private EditText room_id;
    private EditText temp_min;
    private EditText temp_max;
    private EditText humi_min;
    private EditText humi_max;
    private LinearLayout cancel;
    private Button bt_add_node;
    private HttpLoader httpLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_regist);
        httpLoader=new HttpLoader();
        initView();
    }

    public void initView(){
        type_choice=findViewById(R.id.type_choice);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NodeRegistActivity.this, R.layout.spniner_style, getData());//将可选内容与ArrayAdapter连接起来
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //设置下拉列表的风格
        type_choice.setAdapter(adapter);//将adapter2 添加到spinner中
        type_choice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nodeType=typeChoice[i];//节点选择
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        node_num=findViewById(R.id.node_num);
        floor_id=findViewById(R.id.floor_id);
        room_id=findViewById(R.id.room_id);
        temp_min=findViewById(R.id.temp_min);
        temp_max=findViewById(R.id.temp_max);
        humi_min=findViewById(R.id.humi_min);
        humi_max=findViewById(R.id.humi_max);
        bt_add_node=findViewById(R.id.bt_add_node);
        cancel=findViewById(R.id.ll_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NodeRegistActivity.this, MainActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        bt_add_node.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataCheck();
            }
        });
    }

    private void dataCheck(){
        String nodeId=node_num.getText().toString().trim();
        String floorId=floor_id.getText().toString().trim();
        String roomId=room_id.getText().toString().trim();
        String tempMin=temp_min.getText().toString().trim();
        String tempMax=temp_max.getText().toString().trim();
        String humiMin=humi_min.getText().toString().trim();
        String humiMax=humi_max.getText().toString().trim();
        if(nodeType.trim().equals("") || nodeId.equals("") ||floorId.equals("")|| roomId.equals("") ||
                tempMin.equals("")||tempMax.equals("")||humiMin.equals("")||humiMax.equals("")){
            Toast.makeText(getApplicationContext(), "输入不能有空，请检查！",Toast.LENGTH_LONG).show();
        }else if( Integer.parseInt(tempMax)< Integer.parseInt(tempMin) || Integer.parseInt(humiMax)< Integer.parseInt(humiMin)){
            Toast.makeText(getApplicationContext(), "最大值不能小于最小值！",Toast.LENGTH_LONG).show();
        }else{
            addNodeInfo ("admin", nodeId,floorId, roomId,
                    nodeType, Integer.parseInt(humiMax), Integer.parseInt(humiMin),
                    Integer.parseInt(tempMin), Integer.parseInt(tempMax));
        }
    }

    public void addNodeInfo (String username, String dev_eui,String floorId, String roomId,
                             String type, int humiMax, int humiMin, int tempMin, int tempMax){
//        System.out.println("用户名"+username);
//        System.out.println("用dev_eui"+dev_eui);
//        System.out.println("用floor_id"+floor_id);
//        System.out.println("用room_id"+room_id);
//        System.out.println("用type"+type);
//        System.out.println("用humiMax"+humiMax);
//        System.out.println("用humiMin"+humiMin);
//        System.out.println("用tempMin"+tempMin);
//        System.out.println("用tempMax"+tempMax);
        httpLoader.addNodeInfo(username, dev_eui,floorId, roomId,
                type, humiMax, humiMin, tempMin, tempMax).subscribe(new Action1<String>() {
            @Override
            public void call(String flags) {
                System.out.println("数据AddNote"+flags);
                if(flags.equals("1")){
                    Toast.makeText(NodeRegistActivity.this,"该设备已存在",Toast.LENGTH_LONG).show();
                } else if(flags.equals("2")){
                    Toast.makeText(NodeRegistActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    node_num.setText("");
                    floor_id.setText("");
                    room_id.setText("");
                    temp_min.setText("");
                    temp_max.setText("");
                    humi_min.setText("");
                    humi_max.setText("");
                }else if(flags.equals("3")){
                    Toast.makeText(NodeRegistActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //获取失败
                Log.e("TAG", "error message:" + throwable.getMessage());
                System.out.println("异常AddNode"+throwable.getMessage());
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

    private List<String> getData(){
        List<String> dataList=new ArrayList<>();
        dataList.add("温湿度");
        dataList.add("烟雾");
        dataList.add("气体");
        dataList.add("门禁");
        dataList.add("水浸");
        return dataList;
    }
}
