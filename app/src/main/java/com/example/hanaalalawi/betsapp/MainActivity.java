package com.example.hanaalalawi.betsapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottobNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navList);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place,new HomeFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navList =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    android.support.v4.app.Fragment selectedFragment = null;
                    switch (item.getItemId()){
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place,selectedFragment).commit();
                    return true;
                }
            };
    }

