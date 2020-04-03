package com.evilgeniuses.memoryhack.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent;
        Intent intent = new Intent(this, AuntificationActivity.class);

//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            intent = new Intent(this, TabbedActivity.class);
//        } else {
//            intent = new Intent(this, AuthenticationActivity.class);
//        }

        startActivity(intent);
        finish();
    }
}
