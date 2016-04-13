package org.sin.legaldemo;

import android.app.Activity;
import android.content.Intent;

import org.sin.legaldemo.MainActivity;

/**
 * Created by Sin on 2016/4/13.
 */
public class Utils  {
    public static void start_Activity(Activity activity,Class<?> cls){
        Intent intent = new Intent(activity,cls);
        activity.startActivity(intent);
        activity.finish();
    }
}
