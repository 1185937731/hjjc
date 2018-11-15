package com.ccnu.hjjc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccnu.hjjc.R;

public class AdminFragment extends Fragment{
    View view;
    private TextView node_regist;
    private TextView mess_config;
    private TextView user_message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_admin,container,false);
        }
        node_regist = (TextView)view.findViewById(R.id.node_regist);
        mess_config = (TextView)view.findViewById(R.id.mess_config);
        user_message = (TextView)view.findViewById(R.id.user_manage);
//        node_regist.setOnClickListener(this);
//        mess_config.setOnClickListener(this);
//        user_message.setOnClickListener(this);
        return view;
    }

    public void onClick(final View view){


    }
}
