package com.evilgeniuses.memoryhack.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evilgeniuses.memoryhack.Fragments.EmptyFragment;
import com.evilgeniuses.memoryhack.R;
import com.evilgeniuses.memoryhack.api.navigation.SwitchFragmentListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements SwitchFragmentListener {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Главный фрагмент
        EmptyFragment emptyFragment = new EmptyFragment();
        switchTo(emptyFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                EmptyFragment emptyFragment = new EmptyFragment();
                switch (item.getItemId()) {
                    case R.id.siteItem:
                        switchTo(emptyFragment);
                        break;
                    case R.id.dataItem:
                        switchTo(emptyFragment);
                        break;
                    case R.id.listItem:
                        switchTo(emptyFragment);
                        break;
                    case R.id.giftsItem:
                        switchTo(emptyFragment);
                        break;
                    case R.id.profileItem:
                        switchTo(emptyFragment);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void switchTo(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
