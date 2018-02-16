package baghi.naeem.com.assignment6.ui.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastMaker {

    public static void make(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
