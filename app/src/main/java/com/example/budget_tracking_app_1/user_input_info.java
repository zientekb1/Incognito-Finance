package com.example.budget_tracking_app_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class user_input_info extends AppCompatActivity {
    EditText date_edit_text;
    DatePickerDialog datePickerDialog;
    Button return_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_info);
        date_edit_text = findViewById(R.id.Date_editText);
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        date_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(user_input_info.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_edit_text.setText((month+1)+"/"+dayOfMonth+"/"+year);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });


        return_button = findViewById(R.id.Return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_input_info.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}