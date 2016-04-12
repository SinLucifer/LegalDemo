package org.sin.legaldemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.R;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Sin on 2016/4/12.
 */
public class LoginFragment extends Fragment {
    private View view;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;

    private RegisterFragment registerFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_frament,container,false);


        et_username = (EditText)view.findViewById(R.id.et_username);
        et_password = (EditText)view.findViewById(R.id.et_userpwd);

        btn_login = (Button)view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btn_register = (Button)view.findViewById(R.id.btn_register2);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragment = new RegisterFragment();
                FragmentTransaction transaction = getActivity().
                        getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_content,registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    public void login(){
        UserBean user = new UserBean();

        user.setUsername(et_username.getText().toString().trim());
        user.setPassword(et_password.getText().toString().trim());

        user.login(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "登陆成功",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), "登陆失败:" + s,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
