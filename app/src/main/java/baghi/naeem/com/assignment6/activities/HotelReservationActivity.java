package baghi.naeem.com.assignment6.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import baghi.naeem.com.assignment6.Network.INetworkCallback;
import baghi.naeem.com.assignment6.Network.NetworkHandler;
import baghi.naeem.com.assignment6.dao.Command;
import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.entities.Hotel;
import baghi.naeem.com.assignment6.entities.Reservation;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;
import baghi.naeem.com.assignment6.util.LogMaker;

public class HotelReservationActivity extends AppCompatActivity {

    private static final String TAG = "HotelReservationActivity";

    private Hotel hotel;

    private EditText checkInYear;
    private EditText checkInMonth;
    private EditText checkInDay;

    private EditText checkOutYear;
    private EditText checkOutMonth;
    private EditText checkOutDay;

    private DatePickerDialog checkInDialog;
    private DatePickerDialog checkOutDialog;

    private EditText countEditText;
    private Spinner roomTypeSpinner;

    private Button saveReserveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservation);

        String hotelId = getIntent().getStringExtra("hotelId");
        hotel = DataSource.getHotels().get(hotelId);

        TextView hotelName = (TextView) findViewById(R.id.reservation_hotel_name);
        ImageView hotelImage = (ImageView) findViewById(R.id.reservation_hotel_image);

        hotelName.setText(hotel.getName());
        hotelImage.setImageResource(hotel.getImageId());

        this.checkInYear = (EditText) findViewById(R.id.check_in_year);
        this.checkInMonth = (EditText) findViewById(R.id.check_in_month);
        this.checkInDay = (EditText) findViewById(R.id.check_in_day);

        this.checkOutYear = (EditText) findViewById(R.id.check_out_year);
        this.checkOutMonth = (EditText) findViewById(R.id.check_out_month);
        this.checkOutDay = (EditText) findViewById(R.id.check_out_day);

        this.countEditText = (EditText) findViewById(R.id.reservation_count);
        this.roomTypeSpinner = (Spinner) findViewById(R.id.reservation_room_type_spinner);

        this.saveReserveButton = (Button) findViewById(R.id.save_reserve_button);

        View.OnClickListener checkInListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                checkInDialog = new DatePickerDialog(HotelReservationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                checkInYear.setText(year + "");
                                checkInMonth.setText((month + 1) + "");
                                checkInDay.setText(dayOfMonth + "");

                                checkOutYear.setText("");
                                checkOutMonth.setText("");
                                checkOutDay.setText("");
                            }
                        }, year, month, day);

                checkInDialog.show();
            }
        };

        this.checkInYear.setOnClickListener(checkInListener);
        this.checkInMonth.setOnClickListener(checkInListener);
        this.checkInDay.setOnClickListener(checkInListener);

        View.OnClickListener checkOutListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                checkOutDialog = new DatePickerDialog(HotelReservationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                checkOutYear.setText(year + "");
                                checkOutMonth.setText((month + 1) + "");
                                checkOutDay.setText(dayOfMonth + "");
                            }
                        }, year, month, day);

                if(checkInYear.getText() != null && checkInYear.getText().length() > 0) {
                    Calendar minDate = Calendar.getInstance();
                    minDate.set(Calendar.YEAR, Integer.valueOf(checkInYear.getText().toString()));
                    minDate.set(Calendar.MONTH, Integer.valueOf(checkInMonth.getText().toString()) - 1);
                    minDate.set(Calendar.DAY_OF_MONTH, Integer.valueOf(checkInDay.getText().toString()));
                    checkOutDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                }

                checkOutDialog.show();
            }
        };

        this.checkOutYear.setOnClickListener(checkOutListener);
        this.checkOutMonth.setOnClickListener(checkOutListener);
        this.checkOutDay.setOnClickListener(checkOutListener);

        this.saveReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidForm()) {
                    new SaveReservationAsyncTask().execute(new Command("SAVE_RESERVATION", HotelReservationActivity.this, getReservationFromForm()));
                }
            }
        });
    }

    private Reservation getReservationFromForm() {
        Reservation reservation = new Reservation();
        reservation.setHotel(hotel);
        reservation.setCheckInDate(getCheckInDate());
        reservation.setCheckOutDate(getCheckOutDate());
        reservation.setCount(Integer.valueOf(countEditText.getText().toString()));
        reservation.setRoomType(Reservation.RoomType.getByValue(roomTypeSpinner.getSelectedItem().toString()));
        return reservation;
    }

    private Calendar getCheckInDate () {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, Integer.valueOf(checkInYear.getText().toString()));
        calendar.set(Calendar.MONTH, Integer.valueOf(checkInMonth.getText().toString()) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(checkInDay.getText().toString()));
        return calendar;
    }

    private Calendar getCheckOutDate () {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, Integer.valueOf(checkOutYear.getText().toString()));
        calendar.set(Calendar.MONTH, Integer.valueOf(checkOutMonth.getText().toString()) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(checkOutDay.getText().toString()));
        return calendar;
    }

    private boolean isValidForm() {
        if(isNull(checkInYear.getText()) || isNull(checkInMonth.getText()) || isNull(checkInDay.getText())) {
            ToastMaker.make(this, "Check in date is not valid");
            return false;
        }
        if(isNull(checkOutYear.getText()) || isNull(checkOutMonth.getText()) || isNull(checkOutDay.getText())) {
            ToastMaker.make(this, "Check out date is not valid");
            return false;
        }
        if(isNull(countEditText.getText())) {
            ToastMaker.make(this, "Count is not valid");
            return false;
        }
        return true;
    }

    private boolean isNull(Editable text) {
        return text == null || text.toString().length() < 1;
    }

    public static class SaveReservationCallback implements INetworkCallback<Reservation> {

        private Context context;

        SaveReservationCallback(Context context) {
            this.context = context;
        }

        @Override
        public void callback(Reservation reservation, String errorMessage) {
            Log.d(TAG, "Received save user response from Volley ...");

            if ((errorMessage != null) || (reservation == null)) {
                if (TextUtils.isEmpty(errorMessage))
                    errorMessage = "Failed to save new reservation to server!";
                ToastMaker.make(context, errorMessage);
                return;
            }

            new SaveReservationAsyncTask().execute(new Command("INSERT_RESERVATION", context, reservation));
            ToastMaker.make(context, "New reservation added successfully");
        }
    }

    private static class SaveReservationAsyncTask extends AsyncTask<Command, Void, Command> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Command doInBackground(Command... commands) {
            Command command = commands[0];
            Context context = command.getContext();
            switch(command.getOperand()) {
                case "SAVE_RESERVATION":
                    NetworkHandler.getInstance(context).saveReservation((Reservation) command.getData(), new SaveReservationCallback(context));
                    break;
                case "INSERT_RESERVATION":
                    boolean result = DataSource.getDbHelper(context).insertReservation(DataSource.getDbHelper(context).getReadableDatabase(), (Reservation) command.getData());
                    if(result)
                        return command;
                    return new Command(command.getContext(), null);
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Command command) {
            if(command != null && command.getOperand().equals("INSERT_RESERVATION")) {
                if(command.getData() == null) {
                    ToastMaker.make(command.getContext(), "Save reservation failed");
                } else {
                    ToastMaker.make(command.getContext(), "Reservation saved successfully");
                    Intent intent = new Intent(command.getContext(), MainPageActivity.class);
                    command.getContext().startActivity(intent);
                    ((Activity) command.getContext()).finish();
                }
            }
            super.onPostExecute(command);
        }
    }
}