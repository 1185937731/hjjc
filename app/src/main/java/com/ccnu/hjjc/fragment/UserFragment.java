package com.ccnu.hjjc.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccnu.hjjc.Bean.InformReturnObject;
import com.ccnu.hjjc.R;
import com.ccnu.hjjc.activity.ChangeMailActivity;
import com.ccnu.hjjc.activity.ChangeNumberActivity;
import com.ccnu.hjjc.activity.ChangePasswordActivity;
import com.ccnu.hjjc.activity.LoginActivity;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.ccnu.hjjc.util.UserManage;
import com.google.gson.Gson;

import rx.functions.Action1;

public class UserFragment extends Fragment{
    private TextView name;
    private TextView phone_number;
    private TextView email;
    private LinearLayout change_password;
    private LinearLayout logout;
    private LinearLayout exit;
    private LinearLayout change_number;
    private LinearLayout change_mail;

    private String tel_old;
    private String mail_old;
    protected String user_name;

    private HttpLoader httpLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_user,container,false);

        return inflater.inflate(R.layout.fragment_user, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        httpLoader = new HttpLoader();
//        Bundle bundle = getActivity().getIntent().getExtras();
//        user_name = bundle.getString("username");
        user_name=UserManage.getInstance().getUserInfo(getContext()).getUserName();
        change_number = (LinearLayout)getActivity().findViewById(R.id.change_number);
        change_mail = (LinearLayout)getActivity().findViewById(R.id.change_mail);
        name = (TextView) getActivity().findViewById(R.id.name);
        phone_number = (TextView)getActivity().findViewById(R.id.phone_number);
        email = (TextView)getActivity().findViewById(R.id.email);
        change_password = (LinearLayout) getActivity().findViewById(R.id.change_password);
        logout = (LinearLayout) getActivity().findViewById(R.id.logout);
        exit = (LinearLayout)getActivity().findViewById(R.id.exit);

        change_number.setOnClickListener(onclick);
        change_mail.setOnClickListener(onclick) ;
        change_password.setOnClickListener(onclick);
        logout.setOnClickListener(onclick);
        exit.setOnClickListener(onclick);

//        name.setText("admin");
//        infrom ("admin");
//        phone_number.setText(tel_old);
//        System.out.println("number"+tel_old);
//        email.setText(mail_old);

    }

    @Override
    public void onResume() {
        super.onResume();
        name.setText(user_name);
        infrom (user_name);
        phone_number.setText(tel_old);
//        System.out.println("number"+tel_old);
        email.setText(mail_old);
    }

    View.OnClickListener onclick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.change_number:
                    Intent intent1 = new Intent(getActivity(), ChangeNumberActivity.class);
                    intent1.putExtra("user_name",user_name);
                    intent1.putExtra("tel_old",tel_old);
                    startActivity(intent1);
                    break;
                case R.id.change_mail:
                    Intent intent2 = new Intent(getActivity(), ChangeMailActivity.class);
                    intent2.putExtra("mail_old",mail_old);
                    intent2.putExtra("user_name",user_name);
                    startActivity(intent2);
                    break;
                case R.id.change_password:
                    Intent intent3 = new Intent(getActivity(), ChangePasswordActivity.class);
                    intent3.putExtra("user_name",user_name);
                    startActivity(intent3);
                    break;
                case R.id.logout:
                    AlertDialog.Builder isLogout=new AlertDialog.Builder(getActivity());
                    //设置对话框标题
                    isLogout.setTitle("注销账号");
                    //设置对话框消息
                    isLogout.setMessage("注销后需要重新登录，你确定要注销吗？");
                    // 添加选择按钮并注册监听
                    isLogout.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            UserManage.getInstance().clear(getContext());
                            System.out.println("是否清除1"+UserManage.getInstance().hasUserInfo(getContext()));
                            Intent intent4 = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent4);
                            getActivity().finish();

                        }
                    });
                    isLogout.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    //对话框显示
                    isLogout.show();
                    break;
                case R.id.exit:
                    AlertDialog.Builder isExit=new AlertDialog.Builder(getActivity());
                    //设置对话框标题
                    isExit.setTitle("消息提醒");
                    //设置对话框消息
                    isExit.setMessage("你确定要退出吗？");
                    // 添加选择按钮并注册监听
                    isExit.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.out.println("是否清除2"+UserManage.getInstance().hasUserInfo(getContext())+UserManage.getInstance().getUserInfo(getContext()).getAreaName_get()+UserManage.getInstance().getUserInfo(getContext()).getCompanyName_get());
                            getActivity().finish();
                            System.exit(0);
                            dialogInterface.dismiss();

                        }
                    });
                    isExit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    //对话框显示
                    isExit.show();
                    break;
            }
        }

    };


    //网络通信，登陆
    public void infrom (String name){
        httpLoader.infrom(name).subscribe(new Action1<InformReturnObject>() {
            @Override
            public void call(InformReturnObject informReturnObject) {
                System.out.println("数据inform" + new Gson().toJson(informReturnObject));
                //获取成功，数据在loginReturnObject
                tel_old = informReturnObject.getTel();
                mail_old = informReturnObject.getEmail();
                phone_number.setText(tel_old);
                email.setText(mail_old);

//                Toast.makeText(getActivity(), "获取数据" + informReturnObject.getTel() + "&" +informReturnObject.getEmail() ,
//                        Toast.LENGTH_LONG).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //获取失败
                Log.e("TAG", "异常error message:" + throwable.getMessage());
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

    public boolean onKeyDown (int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            return true;
        }
        return false;
    }

}