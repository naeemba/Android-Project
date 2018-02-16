package baghi.naeem.com.assignment6.activities;

import android.content.Intent;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import baghi.naeem.com.assignment6.Network.NetworkHandler;
import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;

public class ServerURLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_url);

        final EditText serverURL = (EditText) findViewById(R.id.server_url);

        Button button = (Button) findViewById(R.id.save_server_url);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(serverURL.getText().toString())) {
                    ToastMaker.make(ServerURLActivity.this, "Please fill server url");
                } else {
                    NetworkHandler.getInstance(ServerURLActivity.this).setServerURL(serverURL.getText().toString());
                    Intent intent = new Intent(ServerURLActivity.this, MainActivity.class);
                    startActivity(intent);
                    ServerURLActivity.this.finish();
                }
            }
        });
    }
}
