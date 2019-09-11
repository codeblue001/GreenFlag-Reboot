package code.blue.greenflag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Account extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
//            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 4 characters
            "$");

    ImageView nextButton;
    ImageView backButton;
    EditText textInputEmail, textInputPassword, textInputRepeatPassword, textInputUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        textInputEmail = findViewById(R.id.emailEditText);
        textInputPassword = findViewById(R.id.passwordlEditText);
        textInputRepeatPassword = findViewById(R.id.repeatPasswordEditText);
        backButton = findViewById(R.id.backButtonImageView);


        nextButton = findViewById(R.id.nextButtonImageView);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() || !validatePassword()){
                    Toast.makeText(Account.this, "Please fix error to move on", Toast.LENGTH_LONG);
                    return;
                }else {
                    Intent intent = new Intent(Account.this, Profile.class);
                    startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateEmail() {
        //https://www.youtube.com/watch?v=cnD_7qFeZcY
        String emailInput = textInputEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field Can't  be Empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return  false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

//    private  boolean validateUsername(){
//        String usernameInput = textInputUsername.getText().toString().trim();
//
//        if (usernameInput.isEmpty()){
//            textInputUsername.setError("Field can't be empty");
//            return false;
//        }else {
//            textInputUsername.setError(null);
//            return true;
//        }
//    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getText().toString().trim();
        String confirmPassword = textInputRepeatPassword.getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        }else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            textInputPassword.setError("Password too weak");
            return false;
        }else if(!passwordInput.equals(confirmPassword)){
            textInputRepeatPassword.setError("Password does not match");
            return false;
        }else {
            textInputPassword.setError(null);
            textInputRepeatPassword.setError(null);
            return true;
        }
    }
}
