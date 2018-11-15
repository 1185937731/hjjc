package com.ccnu.hjjc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccnu.hjjc.R;

public class UserFragment extends Fragment{
    private TextView name;
    private TextView phone_number;
    private TextView email;
    private LinearLayout change_password;
    private LinearLayout logout;
    private LinearLayout exit;
    private LinearLayout change_number;
    private LinearLayout change_mail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_user,container,false);
        name = (TextView)view.findViewById(R.id.name);
        phone_number = (TextView)view.findViewById(R.id.phone_number);
        email = (TextView)view.findViewById(R.id.email);
        change_password = (LinearLayout) view.findViewById(R.id.change_password);
        logout = (LinearLayout) view.findViewById(R.id.logout);
        exit = (LinearLayout)view.findViewById(R.id.exit);
        change_number = (LinearLayout)view.findViewById(R.id.change_number);
        change_mail = (LinearLayout)view.findViewById(R.id.change_mail);
        return inflater.inflate(R.layout.fragment_user, null);
    }
}
