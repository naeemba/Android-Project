package baghi.naeem.com.assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSource {

    private static Map<String, User> users = new HashMap<>();

    private DataSource(){}

    public synchronized static Map<String, User> getUsers() {
        return DataSource.users;
    }

    public static void init() {
        User user = new User();
        user.setFirstName("Naeem");
        user.setLastName("Baghi");
        user.setUsername("naeemba");
        user.setPassword("123456");
        user.setMobileNo("09123456789");
        user.setEmail("naeembaghi1992@gmail.com");
        user.setGender(User.Gender.male);
        user.setLanguage(User.Language.fa);
        user.setSmsNotification(User.NotificationStatus.enabled);
        user.setEmailNotification(User.NotificationStatus.disabled);
        DataSource.getUsers().put(user.getUsername(), user);
    }

    private static List<Hotel> hotels = new ArrayList<>();
    static {
        Hotel hotel = new Hotel("Nihiwatu Resort", "Nusa Tenggara Tim, Hoba Wawi, Wanokaka, Hoba Wawi, Wanokaka, Kabupaten Sumba Barat, Nusa Tenggara Tim., Indonesia", R.drawable.nihiwatu_resort);
        hotels.add(hotel);

        hotel = new Hotel("The Brando Resort", "Onetahi, French Polynesia", R.drawable.the_brando_resort);
        hotels.add(hotel);

        hotel = new Hotel("The Lodge & Spa at Brush Creek Ranch", "66 Brush Creek Ranch Road, Saratoga, WY 82331, USA", R.drawable.the_lodge_spa_at_brush_creek_ranch);
        hotels.add(hotel);

        hotel = new Hotel("The Lodge at Kauri Cliffs", "139 Tepene Tablelands Rd, Matauri Bay 0245, New Zealand", R.drawable.the_lodge_at_kauri_cliffs);
        hotels.add(hotel);

        hotel = new Hotel("Gibbs Farm", "PO BOX 2, Karatu, Tanzania", R.drawable.gibbs_farm);
        hotels.add(hotel);

        hotel = new Hotel("Tswalu Kalahari Reserve", "Tswalu Kalahari Game Reserve, Faans Grove, 8460, South Africa", R.drawable.tswalu_kalahari_reserve);
        hotels.add(hotel);
    }

    public static List<Hotel> getHotels() {
        return DataSource.hotels;
    }
}
