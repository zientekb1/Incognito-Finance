package com.example.budget_tracking_app_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

public class user_input_info extends AppCompatActivity {
    EditText date_edit_text, name_text, income_text;
    DatePickerDialog datePickerDialog;
    Button return_button, enter_button;
    RadioGroup radioGroup;
    RadioButton radioButton;
    double amount_total = 0.0;
    SharedPreferences sharedPreferences;
    public static String AMOUNT_TOTAL_KEY = "amount_total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_info);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Set up the date picker
        date_edit_text = findViewById(R.id.date_edit_text);
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        date_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a dialog to select a date
                datePickerDialog = new DatePickerDialog(user_input_info.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date to the date_edit_text field
                        date_edit_text.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Handle the return button click
        return_button = findViewById(R.id.Return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to the main activity
                Intent intent = new Intent(user_input_info.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Handle the enter button click
        name_text = findViewById(R.id.name_edit_text);
        income_text = findViewById(R.id.Income_edit_text);
        date_edit_text = findViewById(R.id.date_edit_text);
        enter_button = findViewById(R.id.Enter_button);
        radioGroup = findViewById(R.id.radioGroup);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input values
                String name = name_text.getText().toString();
                double income = Double.parseDouble(income_text.getText().toString());
                String date = date_edit_text.getText().toString();
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                // Retrieve the current total amount from SharedPreferences
                amount_total = sharedPreferences.getFloat(AMOUNT_TOTAL_KEY, 0.0f);

                // Update the total amount based on the selected radio button
                if (radioId == R.id.Expense_button) {
                    amount_total -= income;
                } else if (radioId == R.id.Income_button) {
                    amount_total += income;
                }

                // Update the total amount in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(AMOUNT_TOTAL_KEY, (float) amount_total);
                editor.apply();

                // Return the data to the main activity
                Intent intent = new Intent(user_input_info.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("income", income);
                intent.putExtra("date", date);
                intent.putExtra("amount_total", amount_total);
                startActivity(intent);
            }
        });
    }
}
