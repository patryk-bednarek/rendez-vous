package com.example.rendezvous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import ru.dimorinny.showcasecard.ShowCaseView;
import ru.dimorinny.showcasecard.position.ShowCasePosition;
import ru.dimorinny.showcasecard.position.ViewPosition;
import ru.dimorinny.showcasecard.radius.Radius;

public class MainActivity extends AppCompatActivity {

    boolean firstStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTopNavigationView();
    }

    private void setupTopNavigationView() {
        BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(MainActivity.this, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        View profile_view = findViewById(R.id.ic_profile);
        View matches_view = findViewById(R.id.ic_matched);

        if (firstStart) {
            showToolTip_profile(new ViewPosition(profile_view));
        }
        SharedPreferences newPref = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = newPref.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void showToolTip_profile(ShowCasePosition position) {
        new ShowCaseView.Builder(MainActivity.this)
                .withTypedPosition(position)
                .withTypedRadius(new Radius(186F))
                .withContent("First upload your profile picture and click on Confirm other wise your app will not work fine")
                .build()
                .show(MainActivity.this);
    }

    public void DisLikeBtn(View view) {
    }

    public void LikeBtn(View view) {
    }
}