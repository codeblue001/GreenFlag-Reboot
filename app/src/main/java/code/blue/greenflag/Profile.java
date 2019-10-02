package code.blue.greenflag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Profile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    TextView ageTextView, genderTextView;
    EditText nameEditText, usernameEditText, passwordEditText;
    RadioGroup radioGroup;
    Button birthDateButton, saveButton;
    ImageView profileImage, cameraButton;
    Spinner countrySpinner;
    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage = findViewById(R.id.profileImageView);
        ageTextView = findViewById(R.id.ageTextView);
        genderTextView = findViewById(R.id.genderTextView);
        nameEditText = findViewById(R.id.profileNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.profilePasswordEditText);
        radioGroup = findViewById(R.id.radioGroup);
        birthDateButton = findViewById(R.id.selectBirthDateButton);
        saveButton = findViewById(R.id.saveButton);
        cameraButton = findViewById(R.id.changePhotoButton);
        countrySpinner = findViewById(R.id.spinner);

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
                //Todo get Text
                String name = nameEditText.getText().toString();
                Log.i("EditText", name);
                String username = usernameEditText.getText().toString();
                Log.i("EditText", username);
                String password = passwordEditText.getText().toString();
                Log.i("EditText", password);
                String age = ageTextView.getText().toString();
                Log.i("EditText", age);
                String country = countrySpinner.getSelectedItem().toString();
                Log.i("Spinner", country);
                String gender = genderTextView.getText().toString();
                Log.i("RadioButton", gender);

                //Todo conditional for name, username, and password
                //name, username, password
                boolean isEmpty = false;
                if (name.length() == 0 || username.length() == 0 || password.length() == 0 || age.length() == 0) {
                    isEmpty = true;
                }
                //dob, gender, address
                if (gender.equals("none") || country.equals("Not-Specified")) {
                    isEmpty = true;
                }
                if (isEmpty) {
                    String err_mes = "One or more empty fields exists";
                    Toast.makeText(getApplicationContext(), err_mes, Toast.LENGTH_SHORT).show();
                } else {
                    //Toast to display user inputs
                    addUsers(name, username, password, age, gender, country);
                }

                //Todo add intent to open next activity on click
                Intent intent = new Intent(Profile.this, RegisteredUsersDatabase.class);
                startActivity(intent);
            }
        });

        birthDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //Todo Radio button
        //https://www.youtube.com/watch?v=-SdM1EUO1tQ
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                //checkedId means selected button id
                genderTextView.setText(radioButton.getText());
            }
        });

        //Todo set up countrySpinner
        //https://www.youtube.com/watch?v=on_OrrX7Nw4
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);
        countrySpinner.setOnItemSelectedListener(this);
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar today = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();

        dob.set(Calendar.YEAR, year);
        dob.set(Calendar.MONTH, month);
        dob.set(Calendar.DAY_OF_MONTH, day);
        String currentDateString = DateFormat.getDateInstance().format(dob.getTime());

        //Todo Calculate Age
        //https://stackoverflow.com/questions/38967422/calculate-age-from-birthdate
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        ageTextView.setText(ageS);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Todo create database
    public void addUsers(String name, String username, String password,String age, String gender, String country) {

        //Instance will create the database when this activities oncreate method is called
        DatabaseHelper dbHelper = new DatabaseHelper(Profile.this);// Create a new instance
        SQLiteDatabase myDatabaseHelper = dbHelper.getWritableDatabase();

        //use content value to add data int the database
        ContentValues values = new ContentValues();
        values.put(DatabaseUtil.TaskTable.nameColumn, name);
        values.put(DatabaseUtil.TaskTable.userNameColumn, username);
        values.put(DatabaseUtil.TaskTable.passwordColumn, password);
//        values.put(DatabaseUtil.TaskTable.photoColumn, photo);
        values.put(DatabaseUtil.TaskTable.ageColumn, age);
//        values.put(DatabaseUtil.TaskTable.birthDateColumn, birthDate);
        values.put(DatabaseUtil.TaskTable.countryColumn, country);
        values.put(DatabaseUtil.TaskTable.genderColumn, gender);

        myDatabaseHelper.insert(DatabaseUtil.TaskTable.TABLE_NAME, null, values);

        if (myDatabaseHelper.insert(DatabaseUtil.TaskTable.TABLE_NAME, null, values) > 0) {
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ERROR: unable to add User to database", Toast.LENGTH_SHORT).show();
        }
        myDatabaseHelper.close();
    }
}
