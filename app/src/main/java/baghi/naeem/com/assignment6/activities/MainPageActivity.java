package baghi.naeem.com.assignment6.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import baghi.naeem.com.assignment6.dao.Command;
import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.entities.User;
import baghi.naeem.com.assignment6.ui.tools.AboutDialog;
import baghi.naeem.com.assignment6.util.LogMaker;
import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;

public class MainPageActivity extends AppCompatActivity {

    public static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        TextView name = (TextView) findViewById(R.id.main_username);
        name.setText(DataSource.user.getUsername());

        RelativeLayout flightLayout = (RelativeLayout) findViewById(R.id.flight_layout);
        RelativeLayout trainLayout = (RelativeLayout) findViewById(R.id.train_layout);
        RelativeLayout busLayout = (RelativeLayout) findViewById(R.id.bus_layout);
        RelativeLayout hotelLayout = (RelativeLayout) findViewById(R.id.hotel_layout);
        RelativeLayout holidayLayout = (RelativeLayout) findViewById(R.id.holiday_layout);
        RelativeLayout aboutLayout = (RelativeLayout) findViewById(R.id.about_layout);
        RelativeLayout reservationsLayout = (RelativeLayout) findViewById(R.id.reservation_layout);

        flightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMaker.log(MainPageActivity.class.getSimpleName(), "Flight Button Clicked");
                toast("Flight is not implemented yet.");
            }
        });

        trainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMaker.log(MainPageActivity.class.getSimpleName(), "Train Button Clicked");
                toast("Train is not implemented yet.");
            }
        });

        busLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMaker.log(MainPageActivity.class.getSimpleName(), "Bus Button Clicked");
                toast("Bus is not implemented yet.");
            }
        });

        hotelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMaker.log(MainPageActivity.class.getSimpleName(), "Hotel Button Clicked");
                Intent intent = new Intent(MainPageActivity.this, HotelActivity.class);
                startActivity(intent);
            }
        });

        holidayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMaker.log(MainPageActivity.class.getSimpleName(), "Holiday Button Clicked");
                toast("Holiday is not implemented yet.");
            }
        });

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMaker.log(MainPageActivity.class.getSimpleName(), "About Button Clicked");
                new AboutAsyncTask().execute(new Command(MainPageActivity.this, DataSource.user.getUsername()));
            }
        });

        reservationsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogMaker.log(MainPageActivity.class.getSimpleName(), "Reservations Button Clicked");
                Intent intent = new Intent(MainPageActivity.this, ReservationsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toast(String message) {
        ToastMaker.make(this, message);
    }

    private static class AboutAsyncTask extends AsyncTask<Command, Void, Command> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Command doInBackground(Command... commands) {
            Command command = commands[0];
            Context context = command.getContext();
            User user = DataSource.getDbHelper(context).getUserDataByUsername(DataSource.getDbHelper(context).getReadableDatabase(), (String) command.getData());
            if(user == null)
                return null;
            command.setData(user);
            return command;
        }

        @Override
        protected void onPostExecute(Command command) {
            if(command == null)
                return;
            User user = (User) command.getData();
            MainPageActivity.user = user;

            DialogFragment dialog = new AboutDialog();
            dialog.show(((AppCompatActivity) command.getContext()).getSupportFragmentManager(), "AboutDialog");
            super.onPostExecute(command);
        }
    }
}
