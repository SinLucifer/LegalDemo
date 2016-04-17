package org.sin.legaldemo.WelcomeUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.Util.Utils;
import org.sin.legaldemo.MainActivity;
import org.sin.legaldemo.R;

import cn.bmob.v3.listener.SaveListener;


public class LoginFragment extends Fragment {
    private View mView;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;

    private RegisterFragment registerFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = (Button) mView.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            boolean test;

            @Override
            public void onClick(View v) {
                login();
            }
        });

        et_username = (EditText) mView.findViewById(R.id.et_username);
        et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (et_username.getText().toString().trim().length() < 4) {
                        Toast.makeText(getActivity(), "用户名不能小于4个字符",
                                Toast.LENGTH_SHORT).show();
                        btn_login.setEnabled(false);
                    } else {
                        btn_login.setEnabled(true);
                    }
                }
            }
        });
        et_password = (EditText) mView.findViewById(R.id.et_userpwd);
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (et_password.getText().toString().trim().length() < 8) {
                        Toast.makeText(getActivity(), "密码不能小于8个字符",
                                Toast.LENGTH_SHORT).show();
                        btn_login.setEnabled(false);
                    } else {
                        btn_login.setEnabled(true);
                    }
                }
            }
        });
        et_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_UP){
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        login();
                    }
                }
                return false;
            }
        });


        btn_register = (Button) mView.findViewById(R.id.btn_register2);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragment = new RegisterFragment();
                FragmentTransaction transaction = getActivity().
                        getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.welcome_fragment_container, registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return mView;
    }

    public void login(){
        UserBean user = new UserBean();
        user.setUsername(et_username.getText().toString().trim());
        user.setPassword(et_password.getText().toString().trim());

        user.login(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "登陆成功",
                        Toast.LENGTH_SHORT).show();
                Utils.start_Activity(getActivity(),MainActivity.class);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getContext(), "登陆失败:" + s,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
