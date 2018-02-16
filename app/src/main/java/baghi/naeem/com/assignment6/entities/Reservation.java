package baghi.naeem.com.assignment6.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.util.DateUtil;

public class Reservation {

    public static final String GENERAL_CALENDAR_FORMAT = "yyyy MMM dd";

    private String id;
    private Calendar checkInDate;
    private Calendar checkOutDate;
    private Integer count;
    private RoomType roomType;
    private Hotel hotel;

    public Reservation() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Calendar getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Calendar checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Calendar getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Calendar checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public JSONObject toJson() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.getId());
        jsonObject.put("roomType", this.getRoomType());
        jsonObject.put("hotelId", this.getHotel().getId());
        jsonObject.put("checkIn", DateUtil.getFormattedString(this.getCheckInDate()));
        jsonObject.put("checkOut", DateUtil.getFormattedString(this.getCheckOutDate()));
        jsonObject.put("count", this.getCount());
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        this.setId(jsonObject.getString("id"));
        this.setRoomType(RoomType.getByValue(jsonObject.getString("roomType")));
        this.setHotel(DataSource.getHotels().get(jsonObject.getString("hotelId")));
        this.setCheckInDate(DateUtil.getCalendarByString(jsonObject.getString("checkIn")));
        this.setCheckOutDate(DateUtil.getCalendarByString(jsonObject.getString("checkOut")));
        this.setCount(jsonObject.getInt("count"));
    }

    public enum RoomType {
        singleBed("Single-bed"),
        doubleBed("Double-bed"),
        tripleBed("Triple-bed");

        private String value;
        RoomType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static RoomType getByValue(String value) {
            for(RoomType e: RoomType.values())
                if(e.value.equals(value))
                    return e;
            return null;
        }
    }
}
