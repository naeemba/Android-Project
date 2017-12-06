package baghi.naeem.com.assignment4;

import java.util.HashMap;
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
}
