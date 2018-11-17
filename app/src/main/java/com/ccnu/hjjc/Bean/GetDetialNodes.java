package com.ccnu.hjjc.Bean;

import java.util.ArrayList;
import com.ccnu.hjjc.Bean.NodeDetial;
public class GetDetialNodes {
    private ArrayList<NodeDetial> data;

    public GetDetialNodes(ArrayList<NodeDetial> data) {
        this.data = data;
    }
    public ArrayList<NodeDetial> getData() {
        return data;
    }
    public void setData(ArrayList<NodeDetial> data) {
        this.data = data;
    }
}
