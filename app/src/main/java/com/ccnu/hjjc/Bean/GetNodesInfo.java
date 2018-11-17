package com.ccnu.hjjc.Bean;

import java.util.ArrayList;
import com.ccnu.hjjc.Bean.Node;
public class GetNodesInfo {
    private ArrayList<Node> data;

    public GetNodesInfo(ArrayList<Node> data) {
        this.data = data;
    }
    public ArrayList<Node> getData() {
        return data;
    }
    public void setData(ArrayList<Node> data) {
        this.data = data;
    }
}
