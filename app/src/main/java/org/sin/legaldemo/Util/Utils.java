package org.sin.legaldemo.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.MainActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

public class Utils {

    public static void start_Activity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

}
