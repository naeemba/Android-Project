package baghi.naeem.com.assignment6.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.dao.Command;
import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.entities.Reservation;
import baghi.naeem.com.assignment6.ui.tools.ReservationRecyclerAdapter;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;

public class ReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        RecyclerView reservationRecyclerView = (RecyclerView) (this).findViewById(R.id.reservation_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        reservationRecyclerView.setLayoutManager(layoutManager);

        reservationRecyclerView.setHasFixedSize(true);

        ReservationRecyclerAdapter reservationRecyclerAdapter = new ReservationRecyclerAdapter();
        reservationRecyclerAdapter.setOnItemClickListener(new ReservationRecyclerAdapter.ClickListener() {

            @Override
            public void onItemClick(String id) {
                new ReservationAsyncTask().execute(new Command("DELETE_RESERVATION", ReservationsActivity.this, id));
            }
        });
        reservationRecyclerView.setAdapter(reservationRecyclerAdapter);

        new ReservationAsyncTask().execute(new Command("LOAD_RESERVATIONS",this, null));
    }

    private static class ReservationAsyncTask extends AsyncTask<Command, Void, Command> {

        @Override
        protected Command doInBackground(Command... commands) {
            Command command = commands[0];
            final Context context = command.getContext();
            switch(command.getOperand()) {
                case "LOAD_RESERVATIONS":
                    List<Reservation> reservations = DataSource.getDbHelper(context).getReservations(DataSource.getDbHelper(context).getReadableDatabase());
                    command.setData(reservations);
                    return command;
                case "DELETE_RESERVATION":
                    boolean result = DataSource.getDbHelper(context).deleteReservation(DataSource.getDbHelper(context).getReadableDatabase(), (String) command.getData());
                    command.setData(result);
                    return command;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Command command) {
            if(command != null) {
                Context context = command.getContext();
                switch(command.getOperand()) {
                    case "LOAD_RESERVATIONS":
                        RecyclerView reservationRecyclerView = (RecyclerView) ((AppCompatActivity) context).findViewById(R.id.reservation_recycler_view);
                        ReservationRecyclerAdapter reservationRecyclerAdapter = (ReservationRecyclerAdapter) reservationRecyclerView.getAdapter();
                        reservationRecyclerAdapter.setReservations((List<Reservation>) command.getData());
                        reservationRecyclerAdapter.notifyDataSetChanged();
                        break;
                    case "DELETE_RESERVATION":
                        boolean result = (boolean) command.getData();
                        if(result) {
                            ToastMaker.make(context, "Reservation deleted successfully");
                            new ReservationAsyncTask().execute(new Command("LOAD_RESERVATIONS", context, null));
                        } else {
                            ToastMaker.make(context, "Deleting failed");
                        }
                        break;
                }
            }
            super.onPostExecute(command);
        }
    }
}
