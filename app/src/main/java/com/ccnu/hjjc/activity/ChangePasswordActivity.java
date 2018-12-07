package com.ccnu.hjjc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccnu.hjjc.R;
import com.ccnu.hjjc.http.Fault;
import com.ccnu.hjjc.http.HttpLoader;
import com.ccnu.hjjc.util.UserManage;

import java.util.regex.Pattern;

import rx.functions.Action1;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_pwd_old;
    private EditText et_pwd_new;
    private EditText et_pwd_comfirm;
    private Button btn_pwd;

    protected String user_name;
    private String pwd_old;
    private String pwd_new;
    private String pwd_confirm;

    private HttpLoader httpLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_changepassword);
        user_name = getIntent().getStringExtra("user_name");
        et_username = (EditText)findViewById(R.id.et_username);
        et_username.setText(user_name);
        et_pwd_old = (EditText)findViewById(R.id.et_pwd_old);
        et_pwd_new = (EditText)findViewById(R.id.et_pwd_new);
        et_pwd_comfirm = (EditText)findViewById(R.id.et_pwd_confirm);
        btn_pwd = (Button)findViewById(R.id.btn_pwd);

        httpLoader = new HttpLoader();
        btn_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd_old = et_pwd_old.getText().toString().trim();
                pwd_new = et_pwd_new.getText().toString().trim();
                pwd_confirm = et_pwd_comfirm.getText().toString().trim();
                int pwdlength=et_pwd_new.getText().length();

                if(pwd_old.equals("") || pwd_new.equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "请输入密码",Toast.LENGTH_LONG).show();
                }else if(pwdlength < 6||pwdlength > 20){
                    Toast.makeText(ChangePasswordActivity.this, "密码长度应为6到20之间", Toast.LENGTH_LONG).show();
                }else if(isNumeric(pwd_new)||isChar(pwd_new)){
                    Toast.makeText(ChangePasswordActivity.this, "密码不能全为数字或字母", Toast.LENGTH_LONG).show();
                }else if("".equals(pwd_confirm)){
                    Toast.makeText(ChangePasswordActivity.this, "请确认密码", Toast.LENGTH_LONG).show();
                } else if(!pwd_confirm.equals(pwd_new)){
                    Toast.makeText(getApplicationContext(), "密码输入不一致，请重新输入",Toast.LENGTH_LONG).show();
                }else {
                    change_pwd(UserManage.getInstance().getUserInfo(ChangePasswordActivity.this).getUserName(),
                            pwd_old, pwd_new);
                }
            }
        });
    }

    //网络通信，修改密码
    public void change_pwd (String name, String old_pwd, String new_pwd){
        httpLoader.change_pwd(name, old_pwd, new_pwd).subscribe(new Action1<String>() {
            @Override
            public void call(String flag) {
                System.out.println("服务器返回数据" +flag);
                //获取成功，数据在loginReturnObject
//                Toast.makeText(ChangePasswordActivity.this, "获取数据" + flag ,
//                        Toast.LENGTH_SHORT).show();
//                flag = changePhoneObject.getFlag();
                if(flag.equals("1")){
                    Toast.makeText(ChangePasswordActivity.this, "修改成功",Toast.LENGTH_LONG).show();
                    finish();
                }else if(flag.equals("2")){
                    Toast.makeText(ChangePasswordActivity.this, "修改失败",Toast.LENGTH_LONG).show();
                    finish();
                }else if (flag.equals("3")){
                    Toast.makeText(ChangePasswordActivity.this, "旧密码输入错误，请重新输入",Toast.LENGTH_LONG).show();
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

    /**
     * 判断字符串是否只包含数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
//    public static boolean isNumeric(String str) {
//        for (int i = str.length(); --i >= 0; ) {
//            if (!Character.isDigit(str.charAt(i))) {
//                return false;
//            }
//        }
//        return true;
//    }

    /** * 纯字母
     * @param fstrData
     * @return */
    public static boolean isChar(String fstrData){
        for (int i = fstrData.length(); --i >= 0; ) {
            char c = fstrData.charAt(i);
            if (!((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z'))) {
                return false;
            }
        }
        return true;

    }
}
