package baghi.naeem.com.assignment4;

import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String username = getArguments().getString("username");
        Log.e(AboutDialog.class.getSimpleName(), username);
        User user = DataSource.getUsers().get(username);

        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(50,50, 50, 50);

        addItem(linearLayout, "First name: " + user.getFirstName());
        addItem(linearLayout, "Last name: " + user.getLastName());
        addItem(linearLayout, "Username: " + user.getUsername());
        addItem(linearLayout, "Email: " + user.getEmail());
        addItem(linearLayout, "Mobile: " + user.getMobileNo());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Username: " + username)
                .setView(linearLayout);
        return builder.create();
    }

    private void addItem(LinearLayout parent, String item) {
        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(this.getContext());
        textView.setText(item);
        parent.addView(textView);
    }
}
