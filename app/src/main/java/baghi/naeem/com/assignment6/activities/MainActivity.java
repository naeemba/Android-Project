package baghi.naeem.com.assignment6.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import baghi.naeem.com.assignment6.dao.Command;
import baghi.naeem.com.assignment6.dao.DBHelper;
import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.entities.User;
import baghi.naeem.com.assignment6.util.LogMaker;
import baghi.naeem.com.assignment6.Network.NetworkHandler;
import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LoginAsyncTask().execute(new Command("CREATE", this, null));

        NetworkHandler networkHandler = NetworkHandler.getInstance(this.getApplicationContext());
        String serverUrl = networkHandler.getServerURL();
        if(serverUrl == null || serverUrl.length() < 1)
            networkHandler.setServerURL("http://192.168.1.103:5000");

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        LinearLayout signUpLayout = (LinearLayout) findViewById(R.id.sign_up_layout);
        signUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = MainActivity.this.username.getText().toString();
                String password = MainActivity.this.password.getText().toString();

                if(username == null || password == null || username.length() < 1 || password.length() < 1) {
                    ToastMaker.make(MainActivity.this, "Invalid Login");
                    return;
                }

                LogMaker.log(this.getClass().getSimpleName(), "checking username and password");
                new LoginAsyncTask().execute(new Command("CHECK_USERNAME_PASSWORD", MainActivity.this, new String[] {username, password}));
            }
        });

        TextView changeServerURLText = (TextView) findViewById(R.id.change_server_url_text);
        changeServerURLText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServerURLActivity.class);
                startActivity(intent);
            }
        });
    }

    private static class LoginAsyncTask extends AsyncTask<Command, Void, Command> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Command doInBackground(Command... commands) {
            Command command = commands[0];
            switch(command.getOperand()) {
                case "CREATE":
                    DataSource.getDbHelper(command.getContext()).getWritableDatabase();
                    return command;
                case "CHECK_USERNAME_PASSWORD":
                    Context context = command.getContext();
                    String[] data = (String[]) command.getData();
                    boolean isValidLogin = DataSource.getDbHelper(context).existByUsernameAndPassword(DataSource.getDbHelper(context).getReadableDatabase(), data[0], data[1]);
                    if(isValidLogin) {
                        return command;
                    } else {
                        return new Command(command.getOperand(), null, command.getContext(), null);
                    }
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Command command) {
            if(command != null && command.getOperand().equals("CHECK_USERNAME_PASSWORD")) {
                if(command.getData() == null) {
                    ToastMaker.make(command.getContext(), "Invalid username or password");
                } else {
                    String[] data = (String[]) command.getData();
                    NetworkHandler.getInstance(command.getContext()).setCommunicationProperties(data[0], data[1]);

                    DataSource.user = DataSource.getDbHelper(command.getContext()).getUserDataByUsername(DataSource.getDbHelper(command.getContext()).getReadableDatabase(), data[0]);

                    Intent intent = new Intent(command.getContext(), MainPageActivity.class);
                    command.getContext().startActivity(intent);
                    ((Activity) command.getContext()).finish();
                }
            }
            super.onPostExecute(command);
        }

    }
}
