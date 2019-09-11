package code.blue.greenflag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

public class Profile extends AppCompatActivity {
    TextView ageTextView, genderTextView, addressTextView;
    EditText nameEditText, usernameEditText, passwordEditText;
    RadioGroup radioGroup;
    RadioButton femaleChoice, maleChoice, unspecifiedChoice;
    Calendar calendar;
    Button birthDateButton, saveButton;
    ImageView profileImage, cameraButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage = findViewById(R.id.profileImageView);
        ageTextView = findViewById(R.id.ageTextView);
        genderTextView = findViewById(R.id.genderTextView);
        addressTextView = findViewById(R.id.addressEditText);
        nameEditText = findViewById(R.id.profileNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordlEditText);
        radioGroup = findViewById(R.id.radioGroup);
        femaleChoice = findViewById(R.id.femaleRadioButton);
        maleChoice = findViewById(R.id.maleRadioButton);
        unspecifiedChoice = findViewById(R.id.unspecifiedRadioButton);
//        calendar = findViewById(R.id.);
        birthDateButton = findViewById(R.id.selectBirthDateButton);
        saveButton = findViewById(R.id.saveButton);
        cameraButton = findViewById(R.id.changePhotoButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Database.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
        }
    }
}
