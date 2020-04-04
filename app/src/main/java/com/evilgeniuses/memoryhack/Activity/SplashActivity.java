package com.evilgeniuses.memoryhack.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent;
        Intent intent = new Intent(this, AuntificationActivity.class);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, AuntificationActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
