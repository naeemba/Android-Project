package baghi.naeem.com.assignment6.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import baghi.naeem.com.assignment6.Network.INetworkCallback;
import baghi.naeem.com.assignment6.Network.NetworkHandler;
import baghi.naeem.com.assignment6.dao.Command;
import baghi.naeem.com.assignment6.dao.DataSource;
import baghi.naeem.com.assignment6.util.LogMaker;
import baghi.naeem.com.assignment6.R;
import baghi.naeem.com.assignment6.ui.tools.ToastMaker;
import baghi.naeem.com.assignment6.entities.User;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

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
                    new SignUpAsyncTask().execute(new Command("CHECK_USERNAME", SignUpActivity.this, getUserFromForm()));
                }
            }
        });
    }

    private User getUserFromForm() {
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
        return user;
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
        ToastMaker.make(this, param + " is not valid.");
    }

    private boolean isNull(Editable text) {
        return text == null || text.toString().length() < 1;
    }

    public static class SaveUserCallback implements INetworkCallback<User> {

        private Context context;

        SaveUserCallback(Context context) {
            this.context = context;
        }

        @Override
        public void callback(User user, String errorMessage) {
            Log.d(TAG, "Received save user response from Volley ...");

            if ((errorMessage != null) || (user == null)) {
                if (TextUtils.isEmpty(errorMessage))
                    errorMessage = "Failed to save new user to server!";
                ToastMaker.make(context, errorMessage);
                return;
            }

            new SignUpAsyncTask().execute(new Command("SIGN_UP",context, user));
            ToastMaker.make(context, "New user added successfully");
        }
    }

    private static class SignUpAsyncTask extends AsyncTask<Command, Void, Command> {

        @Override
        protected Command doInBackground(Command... commands) {
            Command command = commands[0];
            Context context = command.getContext();
            switch(command.getOperand()) {
                case "CHECK_USERNAME":
                    User user = (User) command.getData();
                    if(DataSource.getDbHelper(context).isUsernameAvailable(DataSource.getDbHelper(context).getReadableDatabase(), user.getUsername()))
                        return command;
                    return new Command(command.getOperand(), command.getContext(), null);
                case "SIGN_UP":
                    boolean result = DataSource.getDbHelper(context).insertUser(DataSource.getDbHelper(context).getReadableDatabase(), (User) command.getData());
                    if(result)
                        return command;
                    return new Command(command.getOperand(), command.getContext(), null);
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Command command) {
            if(command != null) {
                switch(command.getOperand()) {
                    case "CHECK_USERNAME":
                        if(command.getData() == null) {
                            ToastMaker.make(command.getContext(), "Username is taken, choose another one.");
                        } else {
                            this.signUp((User) command.getData(), command.getContext());
                        }
                        break;
                    case "SIGN_UP":
                        if(command.getData() == null) {
                            ToastMaker.make(command.getContext(), "Sign up failed");
                        } else {
                            Intent intent = new Intent(command.getContext(), MainActivity.class);
                            command.getContext().startActivity(intent);
                            ((Activity) command.getContext()).finish();
                        }
                        break;
                    default:
                        break;
                }
            }
            super.onPostExecute(command);
        }

        private void signUp(User user, Context context) {
            LogMaker.log(this.getClass().getSimpleName(), "Inserting user with username: " + user.getUsername());
            NetworkHandler.getInstance(context).saveUser(user, new SaveUserCallback(context));
        }
    }
}
