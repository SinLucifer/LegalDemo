package org.sin.legaldemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.LawyerUserUI.CheckFragment;
import org.sin.legaldemo.NormalUserUI.SelectFragment;
import org.sin.legaldemo.Util.Content;
import org.sin.legaldemo.Util.Utils;
import org.sin.legaldemo.WelcomeUI.WelcomeActivity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int MYTASKISEXIST = 0;               //订单碎片存在判断

    private ShowMyTaskFragment showMyTaskFragment;
    private UserBean user;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {


        user = BmobUser.getCurrentUser(getApplicationContext(), UserBean.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0); //为了设置侧边栏的东西才获得的

        TextView tv_username = (TextView) headerView.findViewById(R.id.tv_username);
        TextView tv_email = (TextView) headerView.findViewById(R.id.tv_email);

        tv_username.setText(user.getUsername());
        tv_email.setText(user.getEmail());

//        if (user.isLayer()) {
//            Log.d("Sin", "is a layer");
//            CheckFragment mFragment = new CheckFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.main_fragment_container, mFragment);
//            transaction.commit();
//        } else {
//            Log.d("Sin", "is not a layer");
//            SelectFragment mFragment = new SelectFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.main_fragment_container, mFragment);
//            transaction.commit();
//        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(MYTASKISEXIST == 1){
            MYTASKISEXIST = 0;
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_toolbar_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_write_off) {
            MYTASKISEXIST = 0;
            UserBean.logOut(this);
            UserBean.getCurrentUser(this);
            Utils.start_Activity(this, WelcomeActivity.class);
        } else if (id == R.id.nav_show_task) {
//            if (MYTASKISEXIST == 0) {
//                MYTASKISEXIST = 1;
//                showMyTaskFragment = new ShowMyTaskFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_fragment_container, showMyTaskFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter{
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                if (UserBean.getCurrentUser(getApplicationContext(),UserBean.class).isLayer()){
                    return CheckFragment.newInstance();
                }else {
                    return SelectFragment.newInstance();
                }
            }else {
                return ShowMyTaskFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
