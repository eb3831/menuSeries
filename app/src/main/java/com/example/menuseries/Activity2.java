package com.example.menuseries;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity implements View.OnCreateContextMenuListener,
        AdapterView.OnItemLongClickListener
{
    ListView lv;
    Intent gi;
    double[] seriesArry;
    String[] disArray;
    double a1, seriesSum, difference;
    int seriesType;
    TextView tvChoice;
    ArrayAdapter<String> adp;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        lv = findViewById(R.id.lv);
        tvChoice = findViewById(R.id.tvChoice);

        gi = getIntent();
        seriesType = gi.getIntExtra("seriesType", 0);
        a1 = gi.getDoubleExtra("a1", 0);
        difference = gi.getDoubleExtra("difference", 0);

        seriesArry = new double[20];
        disArray = new String[20];

        if (seriesType == 0)
        {
            for (int i = 1; i < 21; i++)
            {
                seriesArry[i - 1] = a1 * Math.pow(difference, i - 1);
                disArray[i - 1] =  differentView(seriesArry[i - 1]);
            }
        }
        else
        {
            for (int i = 1; i < 21; i++)
            {
                seriesArry[i - 1] = a1 + ((i - 1) * difference);
                disArray[i - 1] = differentView(seriesArry[i - 1]);
            }
        }


        lv.setOnItemLongClickListener(this);
        lv.setOnCreateContextMenuListener(this);

        adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, disArray);
        lv.setAdapter(adp);
    }

    /**
     *            the function changes the format of the numbers that are either too small or too large
     *
     * @param term The number to be formatted
     * @return A string representation of the number in the appropriate format.
     */
    public static String differentView(double term)
    {
        if (term % 1 == 0 && Math.abs(term) < 10000)
        {
            return String.valueOf((int) term);
        }

        if (Math.abs(term) < 10000 && Math.abs(term) >= 0.001)
        {
            return String.format("%.3f", term);
        }

        int exponent = 0;
        double coefficient = term;

        if (Math.abs(term) >= 1)
        {
            while (Math.abs(coefficient) >= 10)
            {
                coefficient /= 10;
                exponent++;
            }
        }
        else
        {
            while (Math.abs(coefficient) < 1 && coefficient != 0)
            {
                coefficient *= 10;
                exponent--;
            }
        }

        return String.format("%.3f * 10^%d", coefficient, exponent);
    }

    /**
     *          the function calculates and returns the sum of the series based on its type.
     *
     * @return the sum of the series
     */
    private double sumSeries()
    {
        if (seriesType == 0)
        {
            return (a1 * (Math.pow(difference, position) - 1)) / (difference - 1);
        }
        else
        {
            return (position * (a1 + seriesArry[position - 1])) / 2;
        }
    }

    /**
     * the function finishes the activity
     * @param view The view that triggered the click event
     */
    public void clickedBack(View view)
    {
        finish();
    }

    @Override
    /**
     * the function creates a menu
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Choose Option");
        menu.add("Position in series");
        menu.add("Sum till the item");
    }

    @Override
    /**
     * the function The function handles the selection of an item from the context menu and performs
     * an action based on the selection.
     */
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String choice = item.getTitle().toString();

        if(choice.charAt(0) == 'P')
        {
            tvChoice.setText("n = " + position);
            return true;
        }
        else if(choice.charAt(0) == 'S')
        {
            tvChoice.setText("sum = " + sumSeries());
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    /**
     * the function  Handles a long click on an item in the listView
     * and Sets the position variable to the item’s index + 1.
     */
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        position = i + 1;
        return false;
    }
}