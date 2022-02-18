package com.example.rendezvous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BtnDislikeActivity extends AppCompatActivity {

    public static final String TAG = "BtnDislikeActivity";
    public static final int ACTIVITY_NUM = 1;
    private Context mContext = BtnDislikeActivity.this;
    private ImageView dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_dislike);
        
        setupTopNavigationView();
        dislike = findViewById(R.id.dislike);

        Intent intent = getIntent();
        String profileUrl = intent.getStringExtra("url");

        switch (profileUrl) {
            case "deafult":
                Glide.with(mContext).load(R.drawable.profile).into(dislike);
                break;
            default:
                Glide.with(mContext).load(profileUrl).into(dislike);
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent mainIntent = new Intent(BtnDislikeActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        }).start();
    }

    private void setupTopNavigationView() {
        BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext,tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void LikeBtn(View view) {

    }
}