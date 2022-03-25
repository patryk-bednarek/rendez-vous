package com.example.rendezvous;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.rendezvous.matches.MatchesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class TopNavigationViewHelper {
    public static final String TAG = "TopNavigationViewHelper";

    public static void setupTopNavigationView(BottomNavigationViewEx topview) {
        Log.d(TAG, "setupTopNavigationView: setting up navigationview");
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_profile:
                        Intent i = new Intent(context, SettingsActivity.class);
                        context.startActivity(i);
                        Log.d("przejscie", "setupTopNavigationView: setting up navigationview");
                        break;

                    case R.id.ic_matched:
                        Intent j = new Intent(context, MatchesActivity.class);
                        context.startActivity(j);
                        Log.d("przejscie", "setupTopNavigationView: setting up navigationview");
                        break;
                }
                return false;
            }
        });
    }
}
