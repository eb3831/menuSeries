package com.example.menuseries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    EditText etA1;
    EditText etDifference;
    Switch sw;
    int seriesType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etA1 = findViewById(R.id.etA1);
        etDifference = findViewById(R.id.etDifference);
        sw = findViewById(R.id.sw);
    }


    /**
     *              the function checks if the imput from the user is valid
     * @param input the input from the user
     * @return true if valid num else false
     */
    public boolean isValidNum(String input)
    {
        return !((input.equals("")) ||
                (input.equals("-")) ||
                (input.equals(".")) ||
                (input.equals("+")) ||
                (input.equals("+.")) ||
                (input.equals("-.")));
    }

    /**
     *              The function checks whether the switch is on or off
     *              and changes the series type and the hint of etDifference accordingly.
     * @param view The view that triggered the click event
     */
    public void clickedSwitch(View view)
    {
        if (sw.isChecked())
        {
            etDifference.setHint("הכנס הפרש:");
            seriesType = 1;
        }

        else
        {
            etDifference.setHint("הכנס מכפיל:");
            seriesType = 0;
        }
    }

    /**
     *      `       the function Handles the click event for the "Next" button.
     *              Validates the input values, extracts the data,
     *              and starts a new activity with the provided parameters.
     * @param view The view that triggered the click event
     */
    public void clickedNext(View view)
    {
        if (isValidNum(etA1.getText().toString())
                && isValidNum(etDifference.getText().toString()))
        {
            double a1 =  Double.parseDouble(etA1.getText().toString());
            double difference = Double.parseDouble(etDifference.getText().toString());
            Intent intent = new Intent(this, Activity2.class);
            intent.putExtra("difference", difference);
            intent.putExtra("a1", a1);
            intent.putExtra("seriesType", seriesType);
            startActivity(intent);
        }

        else
            Toast.makeText(this, "illegal action! please insert numbers.", Toast.LENGTH_SHORT).show();
    }
}