package com.ccnu.hjjc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ccnu.hjjc.Bean.GetNodesInfo;
import com.ccnu.hjjc.Bean.Node;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.activity.NodeDetialActivity;
import com.ccnu.hjjc.adapter.NodeInfoAdapter;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.ccnu.hjjc.util.UserManage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
public class HomeFragment extends Fragment{
    View view;
    private ListView lv_nodes;
    private NodeInfoAdapter nodeInfoAdapter;
    private List<Node> nodes = new ArrayList<>();
    private String username;
    private HttpLoader httpLoader;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        username=UserManage.getInstance().getUserInfo(getContext()).getUserName();
        if (args != null) {
//            username=args.getString("username");
        }
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_home,container,false);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.main_srl);

        lv_nodes=(ListView)view.findViewById(R.id.lv_nodes);

        nodeInfoAdapter=new NodeInfoAdapter(this.getContext());
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("进入下拉刷新");
                new LoadDataThread().start();
            }
        });

        lv_nodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Node node=nodeInfoAdapter.getNodes(position);
                if (node == null) return;
                System.out.println("要发起的请求是"+node.getFloorId()+";"+node.getRoomId());

                Intent intent=new Intent(getActivity(), NodeDetialActivity.class);
                intent.putExtra("floor_id",node.getFloorId());
                intent.putExtra("room_id",node.getRoomId());
                intent.putExtra("username",username);

                startActivity(intent);

            }
        });

//        NodesInfo(username);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        NodesInfo(username);
    }

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends  Thread{
        @Override
        public void run() {
            try {
                System.out.println("下拉进入新的数据加载线程");
                NodesInfo(username);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }
    //获取当前区域所有数据
    public void NodesInfo (String name){
        httpLoader = new HttpLoader();
        httpLoader.nodesInfo(name).subscribe(new Action1<GetNodesInfo>() {
            @Override
            public void call(GetNodesInfo getNodesInfo) {
                System.out.println("返回的主要页面数据" + new Gson().toJson(getNodesInfo));
                ArrayList<Node> nodesInfo=getNodesInfo.getData();
                for (int i=0;i< nodesInfo.size();i++){
                    System.out.println("详细数据" + new Gson().toJson(nodesInfo.get(i)));
                }
                if(nodesInfo.size()!=0){
                    nodeInfoAdapter.clear();
                    nodeInfoAdapter.addNodes(nodesInfo);
                    lv_nodes.setAdapter(nodeInfoAdapter);
                    nodeInfoAdapter.notifyDataSetChanged();

                    Toast.makeText(getActivity(), "更新成功",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "暂无数据",Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
//
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //获取失败
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "请求失败",Toast.LENGTH_LONG).show();
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
