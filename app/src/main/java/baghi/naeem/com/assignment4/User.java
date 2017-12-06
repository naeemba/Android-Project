package baghi.naeem.com.assignment4;

/**
 * Created by sharp on 11/15/2017.
 */

public class User {

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

    public User() {
    }

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

    public enum Gender {
        male,
        female
    }

    public enum Language {
        fa,
        en,
        hi
    }

    public enum NotificationStatus {
        enabled,
        disabled
    }
}
