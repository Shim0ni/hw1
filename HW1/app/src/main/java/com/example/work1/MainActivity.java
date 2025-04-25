package com.example.work1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Spinner spinner;
    String choice;
    EditText inputValue;
    Button convertButton;
    TextView resultTextView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        spinner = findViewById(R.id.spinner);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Debug log to verify initialization
        Log.d(TAG, "UI components initialized: " +
                (spinner != null) + ", " +
                (inputValue != null) + ", " +
                (convertButton != null) + ", " +
                (resultTextView != null));

        // Set up spinner adapter
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.units,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set up spinner listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "Selected conversion: " + choice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set up convert button listener
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Convert button clicked");
                Toast.makeText(context, "Converting...", Toast.LENGTH_SHORT).show();
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        // Get input value
        String inputStr = inputValue.getText().toString();
        Log.d(TAG, "Input value: " + inputStr);

        if (inputStr.isEmpty()) {
            Toast.makeText(context, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse input value
        double inputVal;
        try {
            inputVal = Double.parseDouble(inputStr);
            Log.d(TAG, "Parsed input: " + inputVal);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Number format exception", e);
            return;
        }

        // Perform conversion based on selected option
        double result = 0;
        String unit = "";

        if (choice == null) {
            Toast.makeText(context, "No conversion type selected", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Converting with choice: " + choice);

        switch (choice) {
            case "Centimeters → Meters":
                result = inputVal / 100;
                unit = "m";
                break;
            case "Meters → Kilometers":
                result = inputVal / 1000;
                unit = "km";
                break;
            case "Celsius → Fahrenheit":
                result = (inputVal * (9.0/5.0)) + 32;
                unit = "°F";
                break;
            case "Fahrenheit → Celsius":
                result = (inputVal - 32) * (5.0/9.0);
                unit = "°C";
                break;
            case "Grams → Kilograms":
                result = inputVal / 1000;
                unit = "kg";
                break;
            default:
                Toast.makeText(context, "Unknown conversion type: " + choice, Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Unknown conversion type: " + choice);
                return;
        }

        Log.d(TAG, "Conversion result: " + result + " " + unit);

        // Format and display the result
        String formattedResult = String.format("%.2f %s", result, unit);
        resultTextView.setText(formattedResult);
        Log.d(TAG, "Set result text: " + formattedResult);
    }
}
