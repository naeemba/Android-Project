package baghi.naeem.com.assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout signUpLayout;
    private Button loginButton;

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataSource.init();
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        this.signUpLayout = (LinearLayout) findViewById(R.id.sign_up_layout);
        signUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

        this.loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = MainActivity.this.username.getText().toString();
                String password = MainActivity.this.password.getText().toString();

                if(username == null || password == null || username.length() < 1 || password.length() < 1) {
                    Toast.makeText(MainActivity.this, "Invalid Login", Toast.LENGTH_LONG).show();
                    return;
                }

                if(DataSource.getUsers().containsKey(username) && DataSource.getUsers().get(username).getPassword().equals(password)) {
                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    MainActivity.this.finish();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
