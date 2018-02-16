package baghi.naeem.com.assignment6.dao;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.entities.Hotel;
import baghi.naeem.com.assignment6.entities.User;

public class DataSource {

    private static DBHelper dbHelper;

    public static User user;

    private DataSource(){}

    public static synchronized DBHelper getDbHelper(Context context) {
        if(dbHelper == null)
            dbHelper = new DBHelper(context);
        return dbHelper;
    }

    private static Map<String, Hotel> hotels = new HashMap<>();
    static {
        Hotel hotel = new Hotel("1", "Nihiwatu Resort", "Nusa Tenggara Tim, Hoba Wawi, Wanokaka, Hoba Wawi, Wanokaka, Kabupaten Sumba Barat, Nusa Tenggara Tim., Indonesia", R.drawable.nihiwatu_resort);
        hotels.put(hotel.getId(), hotel);

        hotel = new Hotel("2", "The Brando Resort", "Onetahi, French Polynesia", R.drawable.the_brando_resort);
        hotels.put(hotel.getId(), hotel);

        hotel = new Hotel("3", "The Lodge & Spa at Brush Creek Ranch", "66 Brush Creek Ranch Road, Saratoga, WY 82331, USA", R.drawable.the_lodge_spa_at_brush_creek_ranch);
        hotels.put(hotel.getId(), hotel);

        hotel = new Hotel("4", "The Lodge at Kauri Cliffs", "139 Tepene Tablelands Rd, Matauri Bay 0245, New Zealand", R.drawable.the_lodge_at_kauri_cliffs);
        hotels.put(hotel.getId(), hotel);

        hotel = new Hotel("5", "Gibbs Farm", "PO BOX 2, Karatu, Tanzania", R.drawable.gibbs_farm);
        hotels.put(hotel.getId(), hotel);

        hotel = new Hotel("6", "Tswalu Kalahari Reserve", "Tswalu Kalahari Game Reserve, Faans Grove, 8460, South Africa", R.drawable.tswalu_kalahari_reserve);
        hotels.put(hotel.getId(), hotel);
    }

    public static Map<String, Hotel> getHotels() {
        return DataSource.hotels;
    }
}
