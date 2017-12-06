package baghi.naeem.com.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        name = findViewById(R.id.main_username);

        User user = DataSource.getUsers().get(getIntent().getStringExtra("username"));
        name.setText(user.getFirstName() + " " + user.getLastName());
    }
}
