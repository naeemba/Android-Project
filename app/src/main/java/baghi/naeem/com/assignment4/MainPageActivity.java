package baghi.naeem.com.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {

    private TextView name;

    private RelativeLayout flightLayout;
    private RelativeLayout trainLayout;
    private RelativeLayout busLayout;
    private RelativeLayout hotelLayout;
    private RelativeLayout holidayLayout;
    private RelativeLayout aboutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        name = (TextView) findViewById(R.id.main_username);
        name.setText(getIntent().getStringExtra("username"));

        flightLayout = (RelativeLayout) findViewById(R.id.flight_layout);
        trainLayout = (RelativeLayout) findViewById(R.id.train_layout);
        busLayout = (RelativeLayout) findViewById(R.id.bus_layout);
        hotelLayout = (RelativeLayout) findViewById(R.id.hotel_layout);
        holidayLayout = (RelativeLayout) findViewById(R.id.holiday_layout);
        aboutLayout = (RelativeLayout) findViewById(R.id.about_layout);

        flightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainPageActivity.class.getSimpleName(), "Flight Button Clicked");
            }
        });

        trainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainPageActivity.class.getSimpleName(), "Train Button Clicked");
            }
        });

        busLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainPageActivity.class.getSimpleName(), "Bus Button Clicked");
            }
        });

        hotelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainPageActivity.class.getSimpleName(), "Hotel Button Clicked");
            }
        });

        holidayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainPageActivity.class.getSimpleName(), "Holiday Button Clicked");
            }
        });

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(MainPageActivity.class.getSimpleName(), "About Button Clicked");
            }
        });
    }
}
