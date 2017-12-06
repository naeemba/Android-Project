package baghi.naeem.com.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phone;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Spinner language;
    private RadioGroup gender;
    private CheckBox smsNotification;
    private CheckBox emailNotification;

    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = (EditText) findViewById(R.id.sign_up_first_name);
        lastName = (EditText) findViewById(R.id.sign_up_last_name);
        phone = (EditText) findViewById(R.id.sign_up_mobile);
        email = (EditText) findViewById(R.id.sign_up_email);
        username = (EditText) findViewById(R.id.sign_up_username);
        password = (EditText) findViewById(R.id.sign_up_password);
        confirmPassword = (EditText) findViewById(R.id.sign_up_confirm_password);

        language = (Spinner) findViewById(R.id.sign_up_language);

        gender = (RadioGroup) findViewById(R.id.sign_up_radio_group);

        smsNotification = (CheckBox) findViewById(R.id.sign_up_sms_checkbox);
        emailNotification = (CheckBox) findViewById(R.id.sign_up_email_checkbox);

        signUp = (Button) findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidForm()) {
                    signUp();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    SignUpActivity.this.finish();
                }
            }
        });
    }

    private void signUp() {
        User user = new User();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setMobileNo(phone.getText().toString());
        user.setEmail(email.getText().toString());
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setLanguage(convertLanguage(language.getSelectedItem().toString()));
        user.setGender(convertGender(((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString()));
        user.setSmsNotification(smsNotification.isChecked() ? User.NotificationStatus.enabled : User.NotificationStatus.disabled);
        user.setEmailNotification(emailNotification.isChecked() ? User.NotificationStatus.enabled : User.NotificationStatus.disabled);
        DataSource.getUsers().put(user.getUsername(), user);
    }

    private User.Gender convertGender(String gender) {
        if(gender.equals(getResources().getString(R.string.male)))
            return User.Gender.male;
        return User.Gender.female;
    }


    private User.Language convertLanguage(String language) {
        if(language.equals(getResources().getStringArray(R.array.languages)[0]))
            return User.Language.en;
        if(language.equals(getResources().getStringArray(R.array.languages)[1]))
            return User.Language.fa;
        return User.Language.hi;
    }

    private boolean isValidForm() {
        if(isNull(firstName.getText())) {
            toast("First name");
            return false;
        }
        if(isNull(lastName.getText())) {
            toast("Last name");
            return false;
        }
        if(isNull(phone.getText())) {
            toast("Phone");
            return false;
        }
        if(isNull(email.getText())) {
            toast("Email");
            return false;
        }
        String emailText = email.getText().toString();
        if(!emailText.contains("@") || emailText.startsWith("@") || emailText.startsWith(".") || emailText.contains("..") || emailText.contains("@.")) {
            toast("Email");
            return false;
        }
        if(isNull(username.getText())) {
            toast("Username");
            return false;
        }
        if(isNull(password.getText())) {
            toast("Password");
            return false;
        }
        if(isNull(confirmPassword.getText())) {
            toast("Confirm Password");
            return false;
        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString())) {
            toast("Password");
            return false;
        }
        return true;
    }

    private void toast(String param) {
        Toast.makeText(this, param + " is not valid.", Toast.LENGTH_LONG).show();
    }

    private boolean isNull(Editable text) {
        return text == null || text.toString().length() < 1;
    }
}
