package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class InputContractActivity extends AppCompatActivity {

    private EditText birthdayText;
    private EditText nameEditText;
    private EditText emailEditText;
    private ImageView imageView;
    private UserDataDatabaseHelper dbHelper;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_contract);
        dbHelper = new UserDataDatabaseHelper(getApplicationContext());
        birthdayText = findViewById(R.id.birthdayEditText);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        imageView = findViewById(R.id.imageView);
    }

    public void showDatePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view1, year1, month1, day1) -> {
                    // Handle the selected date (e.g., update the EditText)
                    String selectedDate = year1 + "-" + (month1 + 1) + "-" + day1;
                    birthdayText.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    public void onSelectAvatar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Profile Image");
        final String[] imageNames = {"image1", "image2", "image3"}; // Replace with your image resource names

        builder.setItems(imageNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                avatar = imageNames[which];
                int resourceIdByName = getResourceIdByName(avatar);
                setImageView(resourceIdByName);
            }
        });

        builder.show();
    }

    private int getResourceIdByName(String resourceName) {
        Resources resources = getResources();
        return resources.getIdentifier(resourceName, "drawable", getPackageName());
    }

    private void setImageView(int imageResource) {
        Drawable drawable = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(drawable);
    }

    public void onSaveClicked(View view) {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String birthday = birthdayText.getText().toString().trim();

        // Check if the name is empty
        if (name.isEmpty()) {
            showToast("Please enter a name.");
            return;
        }

        if (avatar.isEmpty()) {
            showToast("Please select avatar.");
            return;
        }

        // Check if the email is empty and is in a valid format
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address.");
            return;
        }
        if (birthday.isEmpty()) {
            showToast("Please select birthday.");
            return;
        }

        dbHelper.insertUserData(name, email, birthday, avatar);
        // Clear the input fields
        nameEditText.setText("");
        emailEditText.setText("");
        showToast("Data saved successfully.");
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }
}