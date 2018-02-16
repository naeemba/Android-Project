package baghi.naeem.com.assignment6.util;

import android.util.Log;

public class LogMaker {

    private static final String TAG = "ASSIGNMENT_PROJECT";

    // LogMaker.log(this.getClass().getSimpleName(), "");

    public static void log(String clazz, String message) {
        Log.d(TAG, clazz + ", " + message);
    }
}
