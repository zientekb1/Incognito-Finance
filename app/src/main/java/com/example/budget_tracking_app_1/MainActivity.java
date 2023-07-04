package com.example.budget_tracking_app_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> addArray = new ArrayList<String>();
    ListView show;
    Button add_income_button;
    TextView amount_saved_num;
    double amount_total = 0.0;
    SharedPreferences sharedPreferences;
    public static String AMOUNT_TOTAL_KEY = "amount_total";
    public static int REQUEST_CODE_USER_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.Name_list);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve the value of amount_total from SharedPreferences
        amount_total = sharedPreferences.getFloat(AMOUNT_TOTAL_KEY, 0.0f);
        amount_saved_num = findViewById(R.id.amount_saved_num);
        amount_saved_num.setText(String.valueOf(amount_total));

        add_income_button = findViewById(R.id.Add_Budget_or_Expense);
        add_income_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open the user_input_info activity and pass the amount_total value
                Intent intent = new Intent(MainActivity.this, user_input_info.class);
                intent.putExtra("amount_total", amount_total);
                startActivityForResult(intent, REQUEST_CODE_USER_INPUT);
            }
        });

        // Retrieve the values from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            double income = intent.getDoubleExtra("income", 0.0);
            String date = intent.getStringExtra("date");

            // Add name, income, and date to the addArray ArrayList
            addArray.add(name + ": " + income + " (" + date + ")");
        }

        // Create and set the adapter outside the if statement to handle both cases
        CustomArrayAdapter adapter = new CustomArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, addArray);
        show.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_USER_INPUT && resultCode == RESULT_OK) {
            // Retrieve the updated addArray from the result Intent
            ArrayList<String> updatedArray = data.getStringArrayListExtra("updatedArray");

            // Update the addArray in MainActivity with the updatedArray
            addArray.addAll(updatedArray);

            // Update the ListView adapter with the new addArray
            CustomArrayAdapter adapter = new CustomArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, addArray);
            show.setAdapter(adapter);
        }
    }

    class CustomArrayAdapter extends ArrayAdapter<String> {
        public CustomArrayAdapter(MainActivity context, int resource, ArrayList<String> items) {
            super(context, resource, items);
        }

        public View getView(int position, View convertView, android.view.ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            // Set the text color
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setTextColor(Color.parseColor("#B8E1DD"));
            // Set the font size
            textView.setTextSize(30);
            return view;
        }
    }
}
