package com.ccnu.hjjc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ccnu.hjjc.Bean.NodeDetial;
import com.ccnu.hjjc.MyApplication;
import com.ccnu.hjjc.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class NodeDtialAdapter extends BaseAdapter {
    private ArrayList<NodeDetial> nodes;
    private LayoutInflater mInflator;
    private Context context;

    public NodeDtialAdapter(Context context) {
        super();
        nodes=new ArrayList<NodeDetial>();
        this.context=context;
        mInflator = LayoutInflater.from(context);
    }

    public void addNodes(ArrayList<NodeDetial> newnodes) {
        for (int i=0;i< newnodes.size();i++){
            System.out.println("addNodes函数详细数据" + new Gson().toJson(newnodes.get(i)));
        }
        nodes=newnodes;
    }

    public NodeDetial getNodes(int position) {
        return nodes.get(position);
    }

    public void clear() {
        nodes.clear();
    }

    @Override
    public int getCount() {
        return nodes.size();
    }

    @Override
    public Object getItem(int i) {
        return nodes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        // General ListView optimization code.
        System.out.println("详细节点的信息adapter被调用进入.");
        if (view == null) {
            if(mInflator==null){
                System.out.println("mInflator为空");
            }
            view = mInflator.inflate(R.layout.detial_item_node, null);
            viewHolder = new ViewHolder();

            viewHolder.it_dev= (TextView) view.findViewById(R.id.it_dev);
            viewHolder.it_type = (TextView) view.findViewById(R.id.it_type);
            viewHolder.it_value = (TextView) view.findViewById(R.id.it_value);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        NodeDetial node = nodes.get(i);
        String dev_eui = node.getDev_eui();
            String type = node.getType();
            switch (type){
            case "tem_hum":
                type="温湿度";
                break;
            case "smoke":
                type="烟雾";
                break;
            case "door":
                type="门禁";
                break;
            case "water":
                type="水浸";
                break;
        }
        String value=node.getRoom_id();
        if (dev_eui != null && dev_eui.length() > 0) {
            viewHolder.it_dev.setText(dev_eui);
        }
        if (type != null && type.length() > 0){
            viewHolder.it_type.setText(type);
        }
        type=node.getType();
        StringBuilder showStr=new StringBuilder();
        if(node.getOnline().equals("1")){
            if(type.equals("tem_hum")){
                System.out.println("详细节点的信息adapter被调用进入.");
                showStr.append("温度:");
                if(node.getTemp_normal().equals("1")){
                    showStr.append(node.getTemp_value());
                }else{
                    showStr.append("异常");
                }
                showStr.append("\n湿度:");
                if(node.getHumi_normal().equals("1")){
                    showStr.append(node.getHumi_value());
                }else{
                    showStr.append("异常");
                }

            }else{
                if((type.equals("smoke") && node.getSmoke_normal().equals("0")) || (type.equals("door") && node.getDoor_normal().equals("0"))
                        || (type.equals("water") && node.getWater_normal().equals("0"))){
                    showStr.append("异常");
                }else{
                    showStr.append("正常");
                }
            }
        }else{
            showStr.append("离线");
        }
        viewHolder.it_value.setText(showStr.toString());

        return view;
    }

    /**
     * 设置Listview的高度
     */
    class ViewHolder {
        TextView it_dev;
        TextView it_value;
        TextView it_type;
    }
}




