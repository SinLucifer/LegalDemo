package org.sin.legaldemo.WelcomeUI;

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
import org.sin.legaldemo.Util.Utils;
import org.sin.legaldemo.MainActivity;
import org.sin.legaldemo.R;

import cn.bmob.v3.listener.SaveListener;

public class RegisterFragment extends Fragment {
    private View mView;
    private EditText et_username;
    private EditText et_password;
    private EditText et_email;
    private EditText et_phone;
    private EditText et_first_name;
    private EditText et_last_name;

    private UserBean user;
    private boolean user_sex = true;
    private boolean user_isLawyer = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_register, container, false);

        init();

        return mView;
    }

    private void init() {

        Button btn_register = (Button) mView.findViewById(R.id.btn_register);
        et_username = (EditText) mView.findViewById(R.id.et_username);
        et_password = (EditText) mView.findViewById(R.id.et_userpwd);

        et_email = (EditText) mView.findViewById(R.id.et_email);
        et_first_name = (EditText) mView.findViewById(R.id.et_firstname);
        et_last_name = (EditText) mView.findViewById(R.id.et_lastname);
        et_phone = (EditText) mView.findViewById(R.id.et_telephone);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_username.getText().toString().trim().length() < 4) {
                    Toast.makeText(getActivity(), "用户名不能小于4个字符",
                            Toast.LENGTH_SHORT).show();
                } else if (et_password.getText().toString().trim().length() < 8) {
                    Toast.makeText(getActivity(), "密码不能小于8个字符",
                            Toast.LENGTH_SHORT).show();
                } else if (et_first_name.getText().toString().trim().length() == 0 ||
                        et_last_name.getText().toString().trim().length() == 0) {
                    Utils.mToast("为了能够正常使用本软件,请务必输入您的真实姓名");
                } else if (et_phone.getText().toString().trim().length() != 11) {
                    Utils.mToast("电话号码输入有误，请检查位数");
                } else {
                    onRegister();
                }
            }
        });


        RadioGroup rg_sex = (RadioGroup) mView.findViewById(R.id.rg_sex);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_female)
                    user_sex = false;
            }
        });

        RadioGroup rg_isLawyer = (RadioGroup) mView.findViewById(R.id.rg_is_lawyer);
        rg_isLawyer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_lawyer_true)
                    user_isLawyer = true;
            }
        });
    }

    public void onRegister() {
        user = new UserBean();
        user.setUsername(et_username.getText().toString().trim());
        user.setPassword(et_password.getText().toString().trim());
        user.setEmail(et_email.getText().toString().trim());
        user.setSex(user_sex);
        user.setIsLayer(user_isLawyer);
        user.setFirstName(et_first_name.getText().toString().trim());
        user.setLastName(et_last_name.getText().toString().trim());
        user.setMobilePhoneNumber(et_phone.getText().toString().trim());

        user.signUp(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "注册成功",
                        Toast.LENGTH_SHORT).show();

                user.login(getContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Utils.start_Activity(getActivity(), MainActivity.class);
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), "注册失败:" + s,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
