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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMatchActivity extends AppCompatActivity {
    Button dateBtn, timeBtn, addBtn;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TimePicker timePicker1;
    TextView timeValue;
    private String format = "";
    EditText teamOneInput, teamTwoInput;
    String teamOneName, teamTwoName;
    TextView dateUpdate;

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
            }
        };
        dateBtn = (Button) findViewById(R.id.dateButton);
        timeBtn = (Button) findViewById(R.id.timeAdd);

        //add time
        final int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        final int min = myCalendar.get(Calendar.MINUTE);

        showTime(hour, min);
    }

    public void addMatch(View view){
        //upload Match data to DB
        addBtn = (Button) findViewById(R.id.addMatch);
        teamOneInput = (EditText) findViewById(R.id.teamNameF);
        teamTwoInput = (EditText) findViewById(R.id.teamNameS);

        teamOneName = teamOneInput.getText().toString().trim();
        teamTwoName = teamTwoInput.getText().toString().trim();

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();

        String dateString = dateUpdate.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Match").push();
        reference.child("team_one").setValue(teamOneName);
        reference.child("team_two").setValue(teamTwoName);
        reference.child("match_time").setValue(hour+" "+min);
        reference.child("match_date").setValue(dateString);
    }

    public void setTime(View view) {
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();

        TextView timeValue = (TextView) findViewById(R.id.timeAdd);
        timeValue.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

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

    //Adding match date
    public void onClick(View view) {
        new DatePickerDialog(AddMatchActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        dateBtn = (Button) findViewById(R.id.dateButton);
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateUpdate = (TextView) findViewById(R.id.dateText);
        dateUpdate.setText(sdf.format(myCalendar.getTime()));
    }

    //Adding match time
    public void onClickTime(View view) {
//        timePicker1.setVisibility(View.VISIBLE);

        timePicker1.setVisibility(View.GONE);
    }
}
