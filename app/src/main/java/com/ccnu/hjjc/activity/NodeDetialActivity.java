package com.ccnu.hjjc.activity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ccnu.hjjc.Bean.GetDetialNodes;
import com.ccnu.hjjc.Bean.NodeDetial;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.adapter.NodeDtialAdapter;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class NodeDetialActivity extends AppCompatActivity {
    private ListView lv_nodes_detial;
    private NodeDtialAdapter nodeDtialAdapter;
    private List<NodeDetial> nodes = new ArrayList<>();
    private TextView floorTopTitle;
    private String username,floor_id,room_id;
    private HttpLoader httpLoader;
  //  private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.node_detial);

        Intent intent=getIntent();
        floor_id=intent.getStringExtra("floor_id");
        room_id=intent.getStringExtra("room_id");
        username=intent.getStringExtra("username");
        System.out.println("nodeDetial传入的具体参数是是:"+floor_id+",room_id"+room_id+",username"+username);
//        Thread t=new Thread(new MyRunnable());
//        t.start();

//        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.main_srl_detial);
        floorTopTitle=findViewById(R.id.floorTopTitle);
        floorTopTitle.setText(floor_id+"楼"+room_id+"室 详情");
        lv_nodes_detial=(ListView)findViewById(R.id.lv_nodes_detial);
//
        nodeDtialAdapter=new NodeDtialAdapter(NodeDetialActivity.this);

//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                System.out.println("详情页面进入下拉刷新");
//                new LoadDataThread().start();
//            }
//        });

        myDetialNodesInfo(username,floor_id,room_id);
    }

//    public class MyRunnable implements Runnable{
//        public void run(){
//            System.out.println("节点详细信息的run函数");
//        }
//    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends  Thread{
        @Override
        public void run() {
            try {
                System.out.println("下拉进入新的数据加载线程");
                myDetialNodesInfo(username,floor_id,room_id);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }
    //获取当前区域所有数据
    public void myDetialNodesInfo (String name,String floorId ,String roomId){
        httpLoader = new HttpLoader();
        httpLoader.detailNodesInfo(name,floorId,roomId).subscribe(new Action1<GetDetialNodes>() {
            @Override
            public void call(GetDetialNodes getDetialNodes) {
                System.out.println("节点详细信息返回的数据" + new Gson().toJson(getDetialNodes));
                ArrayList<NodeDetial>detialNodes=getDetialNodes.getData();
                for (int i=0;i< detialNodes.size();i++){
                    System.out.println("详细数据" + new Gson().toJson(detialNodes.get(i)));
                }
//                nodeInfoAdapter.clear();
                nodeDtialAdapter.addNodes(detialNodes);
                lv_nodes_detial.setAdapter(nodeDtialAdapter);
                nodeDtialAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NodeDetialActivity.this, "更新成功",Toast.LENGTH_LONG).show();
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
