package org.sin.legaldemo.Util;

import android.app.Activity;
import android.content.Intent;

import org.sin.legaldemo.MainActivity;

public class Utils {
    public static void start_Activity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        activity.finish();
    }
}
