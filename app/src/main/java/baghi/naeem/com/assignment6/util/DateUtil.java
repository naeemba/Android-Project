package baghi.naeem.com.assignment6.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import baghi.naeem.com.assignment6.entities.Reservation;

public class DateUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Reservation.GENERAL_CALENDAR_FORMAT, Locale.ENGLISH);

    public static Calendar getCalendarByString(String dateString) {
        try {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(simpleDateFormat.parse(dateString));
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFormattedString(Calendar calendar) {
        return simpleDateFormat.format(calendar.getTime());
    }
}
