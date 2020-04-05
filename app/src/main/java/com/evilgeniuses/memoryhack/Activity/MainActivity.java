package com.evilgeniuses.memoryhack.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.evilgeniuses.memoryhack.Fragments.AddDataFragment;
import com.evilgeniuses.memoryhack.Fragments.EmptyFragment;
import com.evilgeniuses.memoryhack.Fragments.GiftsFragment;
import com.evilgeniuses.memoryhack.Fragments.ProfileFragment;
import com.evilgeniuses.memoryhack.Fragments.SiteFragment;
import com.evilgeniuses.memoryhack.Fragments.UsersListFragment;
import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements SwitchFragment {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Главный фрагмент
        setFragment(SiteFragment.newInstance(), "1");
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView bottomNavigationView = findViewById(R.id.buttomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.siteItem:
                        setFragment(SiteFragment.newInstance(), "1");
                        break;
                    case R.id.dataItem:
                        AddDataFragment addDataFragment = new AddDataFragment();
                        setFragment(addDataFragment, "Add data Fragment");
                        break;
                    case R.id.listItem:
                        setFragment(UsersListFragment.newInstance(), "1");
                        break;
                    case R.id.giftsItem:
                        setFragment(GiftsFragment.newInstance(), "1");
                        break;
                    case R.id.profileItem:
                        setFragment(ProfileFragment.newInstance(), "1");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void setFragment(Fragment fragment, String title) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
