package org.sin.legaldemo.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.sin.legaldemo.R;

/**
 * Created by Sin on 2016/4/13.
 */
public class WelcomeFragment extends Fragment {
    private View view;
    private Button btn_login;
    private Button btn_register;

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_welcome,container,false);
        init();
        return view;
    }

    public void init(){
        registerFragment = new RegisterFragment();
        loginFragment = new LoginFragment();

        btn_login = (Button)view.findViewById(R.id.btn_welcome_login);
        btn_register = (Button)view.findViewById(R.id.btn_welcome_register);

        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.welcome_fragment_container,loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.welcome_fragment_container,registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}