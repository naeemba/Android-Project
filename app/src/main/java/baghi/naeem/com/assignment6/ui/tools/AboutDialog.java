package baghi.naeem.com.assignment6.ui.tools;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TextView;

import baghi.naeem.com.assignment6.activities.MainPageActivity;
import baghi.naeem.com.assignment6.dao.Command;
import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.entities.User;
import baghi.naeem.com.assignment6.util.LogMaker;

public class AboutDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        User user = MainPageActivity.user;

        LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(50,50, 50, 50);

        addItem(linearLayout, "First name: " + user.getFirstName());
        addItem(linearLayout, "Last name: " + user.getLastName());
        addItem(linearLayout, "Username: " + user.getUsername());
        addItem(linearLayout, "Email: " + user.getEmail());
        addItem(linearLayout, "Mobile: " + user.getMobileNo());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Username: " + user.getUsername()).setView(linearLayout);
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
