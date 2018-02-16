package baghi.naeem.com.assignment6.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String mobileNo;
    private String email;
    private Gender gender;
    private Language language;
    private NotificationStatus smsNotification;
    private NotificationStatus emailNotification;

    public User() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NotificationStatus getSmsNotification() {
        return smsNotification;
    }

    public void setSmsNotification(NotificationStatus smsNotification) {
        this.smsNotification = smsNotification;
    }

    public NotificationStatus getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(NotificationStatus emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.getId());
        jsonObject.put("firstName", this.getFirstName());
        jsonObject.put("lastName", this.getLastName());
        jsonObject.put("username", this.getUsername());
        jsonObject.put("password", this.getPassword());
        jsonObject.put("mobileNo", this.getMobileNo());
        jsonObject.put("email", this.getEmail());
        jsonObject.put("gender", this.getGender().getValue());
        jsonObject.put("language", this.getLanguage().getValue());
        jsonObject.put("smsNotification", this.getSmsNotification().getValue());
        jsonObject.put("emailNotification", this.getEmailNotification().getValue());
        return jsonObject;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        this.setId(jsonObject.getString("id"));
        this.setFirstName(jsonObject.getString("firstName"));
        this.setLastName(jsonObject.getString("lastName"));
        this.setUsername(jsonObject.getString("username"));
        this.setMobileNo(jsonObject.getString("mobileNo"));
        this.setEmail(jsonObject.getString("email"));
        this.setGender(Gender.getByValue(jsonObject.getString("gender")));
        this.setLanguage(Language.getByValue(jsonObject.getString("language")));
        this.setSmsNotification(NotificationStatus.getByValue(jsonObject.getString("smsNotification")));
        this.setEmailNotification(NotificationStatus.getByValue(jsonObject.getString("emailNotification")));
    }

    public enum Gender {
        male("MALE"),
        female("FEMALE");

        private String value;
        Gender(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static Gender getByValue(String value) {
            for(Gender e: Gender.values())
                if(e.value.equals(value))
                    return e;
            return null;
        }
    }

    public enum Language {
        fa("FA"),
        en("EN"),
        hi("HI");

        private String value;
        Language(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static Language getByValue(String value) {
            for(Language e: Language.values())
                if(e.value.equals(value))
                    return e;
            return null;
        }
    }

    public enum NotificationStatus {
        enabled("ENABLED"),
        disabled("DISABLED");

        private String value;
        NotificationStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static NotificationStatus getByValue(String value) {
            for(NotificationStatus e: NotificationStatus.values())
                if(e.value.equals(value))
                    return e;
            return null;
        }
    }
}
