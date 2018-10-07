package com.example.hanaalalawi.betsapp;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AdminHomeActivity extends AppCompatActivity {
    Button dateBtn;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottobNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navList);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, new HomeFragment()).commit();

        myCalendar = Calendar.getInstance();

         date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //updateLabel();

            }
        };
        dateBtn = (Button) findViewById(R.id.dateButton);

    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateBtn.setText(sdf.format(myCalendar.getTime()));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navList =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    android.support.v4.app.Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_addMatch:
                            selectedFragment = new AddMatchFragment();
                            break;
                        case R.id.nav_result:
                            selectedFragment = new ResultFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, selectedFragment).commit();
                    return true;
                }
            };

    public void onClick(View view) {
        new DatePickerDialog(AdminHomeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        TextView dateUpdate = (TextView) findViewById(R.id.dateText);
        dateUpdate.setText(sdf.format(myCalendar.getTime()));
    }
}
