package baghi.naeem.com.assignment4;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sharp on 11/15/2017.
 */

public class DataSource {

    private static Map<String, User> users = new HashMap<>();

    private DataSource(){}

    public synchronized static Map<String, User> getUsers() {
        return DataSource.users;
    }
}
