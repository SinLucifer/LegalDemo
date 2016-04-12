package org.sin.legaldemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.R;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Sin on 2016/4/12.
 */
public class RegisterFragment extends Fragment {
    private View view;
    private EditText et_username;
    private EditText et_password;
    private EditText et_nickname;
    private RadioGroup rg_sex;
    private RadioGroup rg_isLawyer;
    private Button btn_register;

    private String user_name;
    private String user_passwd;
    private String user_nickname;
    private boolean user_sex = true;
    private boolean user_isLawyer = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_fragment,container,false);

        init();

        return view;
    }

    private void init(){
        et_username = (EditText)view.findViewById(R.id.et_username);
        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (et_username.getText().toString().trim().length() < 4){
                        Toast.makeText(getActivity(), "用户名不能小于4个字符",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        et_password = (EditText)view.findViewById(R.id.et_userpwd);
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (et_password.getText().toString().trim().length() < 8){
                        Toast.makeText(getActivity(), "密码不能小于8个字符",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        et_nickname = (EditText)view.findViewById(R.id.et_nickname);

        rg_sex = (RadioGroup)view.findViewById(R.id.rg_sex);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_female)
                    user_sex = false;
            }
        });

        rg_isLawyer = (RadioGroup)view.findViewById(R.id.rg_is_lawyer);
        rg_isLawyer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_lawyer_true)
                    user_isLawyer = true;
            }
        });

        btn_register = (Button)view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

    }

    public void onRegister(){
        UserBean user = new UserBean();
        user.setUsername(et_username.getText().toString().trim());
        user.setPassword(et_password.getText().toString().trim());
        user.setNick(et_nickname.getText().toString().trim());
        user.setSex(user_sex);
        user.setIsLayer(user_isLawyer);

        user.signUp(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "注册成功",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), "注册失败:"+s,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
