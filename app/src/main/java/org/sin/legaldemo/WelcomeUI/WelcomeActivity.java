package org.sin.legaldemo.WelcomeUI;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import org.sin.legaldemo.MainActivity;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.Utils;
import org.sin.legaldemo.JavaBean.UserBean;

import cn.bmob.v3.Bmob;


public class WelcomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Bmob.initialize(this, "3301002323635c3de86e559312f80d70");

        if (UserBean.getCurrentUser(this) != null) {
            Utils.start_Activity(this, MainActivity.class);
        }

        WelcomeFragment mFragment = new WelcomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.welcome_fragment_container, mFragment);
        transaction.commit();
    }

}
