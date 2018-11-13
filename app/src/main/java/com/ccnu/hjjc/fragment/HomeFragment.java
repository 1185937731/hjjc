package com.ccnu.hjjc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccnu.hjjc.R;

public class HomeFragment extends Fragment{
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home,container,false);
        }
        ((TextView)view.findViewById(R.id.home)).setText("主页");
        return view;
    }
}
