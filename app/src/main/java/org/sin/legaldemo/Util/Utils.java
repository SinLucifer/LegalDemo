package org.sin.legaldemo.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.MainActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

public class Utils {

    static String result;

    public static void start_Activity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }

    public static String getUserNick(Context context, String ObjectId) {
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();

        query.getObject(context, ObjectId, new GetListener<UserBean>() {

            @Override
            public void onSuccess(UserBean object) {
                // TODO Auto-generated method stub
               result = object.getNick();
            }

            @Override
            public void onFailure(int code, String arg0) {
                // TODO Auto-generated method stub
                result = null;
            }

        });
        return result;
    }
}
