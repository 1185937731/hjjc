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
import android.widget.Toast;


import com.ccnu.hjjc.Bean.RoomGetReturnObject;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.ccnu.hjjc.util.UserManage;
import com.google.gson.Gson;

import org.json.JSONArray;

import rx.functions.Action1;

public class MessConfigActivity extends AppCompatActivity {

    private EditText room_id;
    private EditText floor_id;
    private EditText temp_min;
    private EditText temp_max;
    private EditText humi_min;
    private EditText humi_max;
    private LinearLayout cancel;
    private Button bt_config_it;
    private Button bt_config_all;
    private String roomid;
    private String floorid;
    private HttpLoader httpLoader;
    private String userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_config);
        httpLoader=new HttpLoader();
        userName=UserManage.getInstance().getUserInfo(MessConfigActivity.this).getUserName();
        initView();
    }
    public void initView(){
        floor_id=(EditText)findViewById(R.id.floor_id_1);
        room_id=(EditText)findViewById(R.id.room_id_1);
        temp_min=findViewById(R.id.temp_min_1);
        temp_max=findViewById(R.id.temp_max_1);
        humi_min=findViewById(R.id.humi_min_1);
        humi_max=findViewById(R.id.humi_max_1);
        bt_config_all=findViewById(R.id.bt_config_all);
        bt_config_it=findViewById(R.id.bt_config_it);
        cancel=findViewById(R.id.ll_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessConfigActivity.this, MainActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        room_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("获取数据信息1");
                if(!floor_id.getText().toString().trim().isEmpty() && !room_id.getText().toString().trim().isEmpty()){
                    System.out.println("获取数据信息11");
                    roomGet (userName,floor_id.getText().toString().trim(), room_id.getText().toString().trim());
                }
            }
        });
        floor_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("获取数据信息2");
                if(!floor_id.getText().toString().trim().isEmpty() && !room_id.getText().toString().trim().isEmpty()){
                    System.out.println("获取数据信息22");
                    roomGet (userName,floor_id.getText().toString().trim(), room_id.getText().toString().trim());
                }
            }
        });

        bt_config_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOne();
            }
        });

        bt_config_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAll();
            }
        });
    }

    public void roomGet (String username,String floorId, String roomId) {
        System.out.println("获取数据信息3");
        httpLoader.roomGet(username,floorId, roomId).subscribe(new Action1<RoomGetReturnObject>() {
            @Override
            public void call(RoomGetReturnObject roomgetReturnObject) {
                System.out.println("数据是什么" + new Gson().toJson(roomgetReturnObject));
                int flag=roomgetReturnObject.getFlag();
                System.out.println("获取数据信息90");
                if(flag==0){
                    Toast.makeText(getApplicationContext(), "该房间号不存在",Toast.LENGTH_LONG).show();
                    System.out.println("获取数据信息5 flag==0");
                }else if(flag==1){
//                    Toast.makeText(getApplicationContext(), "该房间号存在",Toast.LENGTH_LONG).show();
                    System.out.println("获取数据信息6 flag==1"+roomgetReturnObject.getData().getTem_low_threshold());
                    RoomGetReturnObject.DataBean dataBean=roomgetReturnObject.getData();
                    temp_min.setText(roomgetReturnObject.getData().getTem_low_threshold()+"");
                    temp_max.setText(roomgetReturnObject.getData().getTem_high_threshold()+"");
                    humi_min.setText(roomgetReturnObject.getData().getHum_low_threshold()+"");
                    humi_max.setText(roomgetReturnObject.getData().getHum_high_threshold()+"");
                } else if(flag==2){
                    Toast.makeText(getApplicationContext(), "该房间号存在",Toast.LENGTH_LONG).show();
                    System.out.println("获取数据信息7 flag==2");
//                    temp_min.setText(roomgetReturnObject.getData().getTem_low_threshold());
//                    temp_max.setText(roomgetReturnObject.getData().getTem_high_threshold());
//                    humi_min.setText(roomgetReturnObject.getData().getHum_low_threshold());
//                    humi_max.setText(roomgetReturnObject.getData().getHum_high_threshold());
                    System.out.println("获取数据信息4");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("获取数据信息8异常处理787");
                //获取失败
                Log.e("TAG", "异常处理787error message:" + throwable.getMessage());
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

    //配置一个房间节点
    private void addOne(){
        String floorId=floor_id.getText().toString().trim();
        String roomId=room_id.getText().toString().trim();
        String tempMin=temp_min.getText().toString().trim();
        String tempMax=temp_max.getText().toString().trim();
        String humiMin=humi_min.getText().toString().trim();
        String humiMax=humi_max.getText().toString().trim();
        if(floorId.equals("")|| roomId.equals("") ||
                tempMin.equals("")||tempMax.equals("")||humiMin.equals("")||humiMax.equals("")){
            Toast.makeText(getApplicationContext(), "输入不能有空，请检查！",Toast.LENGTH_LONG).show();
        }else if( Integer.parseInt(tempMax)< Integer.parseInt(tempMin) || Integer.parseInt(humiMax)< Integer.parseInt(humiMin)){
            Toast.makeText(getApplicationContext(), "最大值不能小于最小值！",Toast.LENGTH_LONG).show();
        }else{
            thesholdOne (userName, floorId, roomId, Integer.parseInt(humiMax), Integer.parseInt(humiMin),
                    Integer.parseInt(tempMin), Integer.parseInt(tempMax));
        }
    }

    //网络请求，配置一个房间
    public void thesholdOne (String username, String floor_id, String room_id,
                             int humiMax, int humiMin, int tempMin, int tempMax) {
        httpLoader.theshold(username,  floor_id,  room_id,
                humiMax,  humiMin,  tempMin,  tempMax).subscribe(new Action1<String>() {
            @Override
            public void call(String flags) {
                if(flags.equals("0")){
                    Toast.makeText(MessConfigActivity.this,"未注册信息",Toast.LENGTH_LONG).show();
                } else if(flags.equals("1")){
                    Toast.makeText(MessConfigActivity.this,"配置成功",Toast.LENGTH_LONG).show();
                }else if(flags.equals("2")){
                    Toast.makeText(MessConfigActivity.this,"配置失败",Toast.LENGTH_LONG).show();
                }
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


    //配置所有房间节点
    private void addAll(){
        String tempMin=temp_min.getText().toString().trim();
        String tempMax=temp_max.getText().toString().trim();
        String humiMin=humi_min.getText().toString().trim();
        String humiMax=humi_max.getText().toString().trim();
        if(tempMin.equals("")||tempMax.equals("")||humiMin.equals("")||humiMax.equals("")){
            Toast.makeText(getApplicationContext(), "输入不能有空，请检查！",Toast.LENGTH_LONG).show();
        }else if( Integer.parseInt(tempMax)< Integer.parseInt(tempMin) || Integer.parseInt(humiMax)< Integer.parseInt(humiMin)){
            Toast.makeText(getApplicationContext(), "最大值不能小于最小值！",Toast.LENGTH_LONG).show();
        }else{
            thesholdAll (userName, Integer.parseInt(humiMax), Integer.parseInt(humiMin),
                    Integer.parseInt(tempMin), Integer.parseInt(tempMax));
        }
    }

    //网络请求，配置所有房间
    public void thesholdAll (String username, int humiMax, int humiMin, int tempMin, int tempMax) {
        httpLoader.thesholdall(username, humiMax,  humiMin,  tempMin,  tempMax).subscribe(new Action1<String>() {
            @Override
            public void call(String flags) {
                if(flags.equals("0")){
                    Toast.makeText(MessConfigActivity.this,"未注册信息",Toast.LENGTH_LONG).show();
                } else if(flags.equals("1")){
                    Toast.makeText(MessConfigActivity.this,"配置成功",Toast.LENGTH_LONG).show();
                }else if(flags.equals("2")){
                    Toast.makeText(MessConfigActivity.this,"配置失败",Toast.LENGTH_LONG).show();
                }
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
