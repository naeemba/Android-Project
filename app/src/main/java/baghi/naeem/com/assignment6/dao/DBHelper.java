package baghi.naeem.com.assignment6.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import baghi.naeem.com.assignment6.Network.NetworkHandler;
import baghi.naeem.com.assignment6.entities.Reservation;
import baghi.naeem.com.assignment6.util.DateUtil;
import baghi.naeem.com.assignment6.util.LogMaker;
import baghi.naeem.com.assignment6.activities.MainActivity;
import baghi.naeem.com.assignment6.activities.MainPageActivity;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;
import baghi.naeem.com.assignment6.entities.User;

public class DBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "hotel_manager";
    private final static Integer DB_VERSION = 14;

    // User table specifications
    private final static String USER_TABLE_NAME = "user";
    private final static String USER_ID = "user_id";
    private final static String USER_FIRST_NAME = "first_name";
    private final static String USER_LAST_NAME = "last_name";
    private final static String USER_MOBILE_NO = "mobile_no";
    private final static String USER_EMAIL = "email";
    private final static String USER_USERNAME = "username";
    private final static String USER_PASSWORD = "password";
    private final static String USER_LANGUAGE = "language";
    private final static String USER_GENDER = "gender";
    private final static String USER_EMAIL_NOTIFICATION = "email_notification";
    private final static String USER_SMS_NOTIFICATION = "sms_notification";
    private static final String USER_CREATE_STATEMENT = "CREATE TABLE " + USER_TABLE_NAME + " ( " +
            USER_ID + " INT UNIQUE," +
            USER_FIRST_NAME + " TEXT NOT NULL," +
            USER_LAST_NAME + " TEXT NOT NULL," +
            USER_MOBILE_NO + " TEXT NOT NULL," +
            USER_EMAIL + " TEXT NOT NULL," +
            USER_USERNAME + " TEXT NOT NULL," +
            USER_PASSWORD + " TEXT NOT NULL," +
            USER_LANGUAGE + " TEXT," +
            USER_GENDER + " INTEGER," +
            USER_EMAIL_NOTIFICATION + " INTEGER," +
            USER_SMS_NOTIFICATION + " INTEGER" +
            " );";
    private static final String USER_DROP_STATEMENT = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    // Reservation table specification
    private static final String RESERVATION_TABLE_NAME = "reservation";
    private static final String RESERVATION_ID = "reservation_id";
    private static final String RESERVATION_CHECK_IN = "reservation_check_in";
    private static final String RESERVATION_CHECK_OUT = "reservation_check_out";
    private static final String RESERVATION_COUNT = "reservation_count";
    private static final String RESERVATION_ROOM_TYPE = "reservation_room_type";
    private static final String RESERVATION_HOTEL_ID = "reservation_hotel_id";
    private static final String RESERVATION_CREATE_STATEMENT = "CREATE TABLE " + RESERVATION_TABLE_NAME + " ( " +
            RESERVATION_ID + " INT UNIQUE," +
            RESERVATION_CHECK_IN + " TEXT NOT NULL," +
            RESERVATION_CHECK_OUT + " TEXT NOT NULL," +
            RESERVATION_COUNT + " INTEGER NOT NULL," +
            RESERVATION_ROOM_TYPE + " TEXT NOT NULL," +
            RESERVATION_HOTEL_ID + " TEXT NOT NULL," +
            USER_ID + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + USER_ID + ") REFERENCES " + USER_TABLE_NAME + " (" + USER_ID + ")" +
            ");";
    private static final String RESERVATION_DROP_STATEMENT = "DROP TABLE IF EXISTS " + RESERVATION_TABLE_NAME + ";";

    private Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        LogMaker.log(this.getClass().getSimpleName(), "constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_CREATE_STATEMENT);
        db.execSQL(RESERVATION_CREATE_STATEMENT);
        LogMaker.log(this.getClass().getSimpleName(), "on create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(USER_DROP_STATEMENT);
        db.execSQL(RESERVATION_DROP_STATEMENT);
        LogMaker.log(this.getClass().getSimpleName(), "on upgrade");
        this.onCreate(db);
    }

    public boolean existByUsernameAndPassword(SQLiteDatabase database, String username, String password) {
        boolean result = database.query(
                DBHelper.USER_TABLE_NAME,
                null,
                "username=? and password=?",
                new String[]{username, password},
                null,
                null,
                DBHelper.USER_ID + " DESC").moveToFirst();
        database.close();
        LogMaker.log(this.getClass().getSimpleName(), "finding user with username " + username + " was " + ((result)? "successfully" : "failed"));
        return result;
    }

    public boolean isUsernameAvailable(SQLiteDatabase database, String username) {
        boolean result = database.query(
                DBHelper.USER_TABLE_NAME,
                null,
                "username=?",
                new String[] {username},
                null,
                null,
                DBHelper.USER_ID + " DESC").moveToFirst();
        database.close();
        LogMaker.log(this.getClass().getSimpleName(), "is username available: " + username);
        return !result;
    }

    public boolean insertUser(SQLiteDatabase database, User user) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.USER_ID, user.getId());
            contentValues.put(DBHelper.USER_FIRST_NAME, user.getFirstName());
            contentValues.put(DBHelper.USER_LAST_NAME, user.getLastName());
            contentValues.put(DBHelper.USER_MOBILE_NO, user.getMobileNo());
            contentValues.put(DBHelper.USER_EMAIL, user.getEmail());
            contentValues.put(DBHelper.USER_USERNAME, user.getUsername());
            contentValues.put(DBHelper.USER_PASSWORD, user.getPassword());
            contentValues.put(DBHelper.USER_LANGUAGE, user.getLanguage().toString());
            contentValues.put(DBHelper.USER_GENDER, user.getGender().getValue());
            contentValues.put(DBHelper.USER_EMAIL_NOTIFICATION, user.getEmailNotification().getValue());
            contentValues.put(DBHelper.USER_SMS_NOTIFICATION, user.getSmsNotification().getValue());

            database.insert(DBHelper.USER_TABLE_NAME, null, contentValues);
            database.close();
            LogMaker.log(this.getClass().getSimpleName(), "user inserted successfully " + user.getUsername());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getReservations(SQLiteDatabase database) {
        List<Reservation> reservations = new ArrayList<>();
        try {
            Cursor cursor = database.query(
                    DBHelper.RESERVATION_TABLE_NAME,
                    new String[]{
                            DBHelper.RESERVATION_ID,
                            DBHelper.RESERVATION_CHECK_IN,
                            DBHelper.RESERVATION_CHECK_OUT,
                            DBHelper.RESERVATION_HOTEL_ID,
                            DBHelper.RESERVATION_ROOM_TYPE,
                            DBHelper.RESERVATION_COUNT
                    },
                    USER_ID + "=?",
                    new String[]{DataSource.user.getId()},
                    null,
                    null,
                    DBHelper.RESERVATION_ID + " DESC");

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(cursor.getString(0));
                    reservation.setCheckInDate(DateUtil.getCalendarByString(cursor.getString(1)));
                    reservation.setCheckOutDate(DateUtil.getCalendarByString(cursor.getString(2)));
                    reservation.setHotel(DataSource.getHotels().get(cursor.getString(3)));
                    reservation.setRoomType(Reservation.RoomType.getByValue(cursor.getString(4)));
                    reservation.setCount(cursor.getInt(5));
                    reservations.add(reservation);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
        return reservations;
    }

    public boolean deleteReservation(SQLiteDatabase database, String id) {
        return database.delete(RESERVATION_TABLE_NAME, RESERVATION_ID + "=?" , new String[] {id}) > 0;
    }

    public boolean insertReservation(SQLiteDatabase database, Reservation reservation) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Reservation.GENERAL_CALENDAR_FORMAT, Locale.US);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.RESERVATION_ID, reservation.getId());
            contentValues.put(DBHelper.RESERVATION_CHECK_IN, simpleDateFormat.format(reservation.getCheckInDate().getTime()));
            contentValues.put(DBHelper.RESERVATION_CHECK_OUT, simpleDateFormat.format(reservation.getCheckOutDate().getTime()));
            contentValues.put(DBHelper.RESERVATION_HOTEL_ID, reservation.getHotel().getId());
            contentValues.put(DBHelper.RESERVATION_COUNT, reservation.getCount());
            contentValues.put(DBHelper.RESERVATION_ROOM_TYPE, reservation.getRoomType().getValue());
            contentValues.put(DBHelper.USER_ID, DataSource.user.getId());

            database.insert(DBHelper.RESERVATION_TABLE_NAME, null, contentValues);
            database.close();
            LogMaker.log(this.getClass().getSimpleName(), "reservation inserted successfully " + reservation.getId());
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public User getUserDataByUsername(SQLiteDatabase database, String username) {
        Cursor cursor = database.query(
                DBHelper.USER_TABLE_NAME,
                new String[]{DBHelper.USER_FIRST_NAME, DBHelper.USER_LAST_NAME, DBHelper.USER_USERNAME, DBHelper.USER_EMAIL, DBHelper.USER_MOBILE_NO, DBHelper.USER_ID},
                "username=?",
                new String[]{username},
                null,
                null,
                DBHelper.USER_ID + " DESC");

        if (cursor.moveToFirst()) {
            LogMaker.log(this.getClass().getSimpleName(), "user with username " + username + " found");
            User user = new User();
            user.setFirstName(cursor.getString(0));
            user.setLastName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setMobileNo(cursor.getString(4));
            user.setId(cursor.getString(5));

            cursor.close();
            database.close();
            return user;
        }
        LogMaker.log(this.getClass().getSimpleName(), "user with username " + username + " not found");

        cursor.close();
        database.close();
        return null;
    }
}
