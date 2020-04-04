package com.evilgeniuses.memoryhack.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.evilgeniuses.memoryhack.Fragments.PhoneNumberFragment;
import com.evilgeniuses.memoryhack.Fragments.ProfileFragment;
import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.R;

public class AuntificationActivity extends AppCompatActivity implements View.OnClickListener, SwitchFragment {

    ImageView imageViewBack;
    TextView textViewTitle;

    Fragment fragmentBack;
    String titleBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auntification_activity);

        imageViewBack = findViewById(R.id.imageViewBack);
        textViewTitle = findViewById(R.id.textViewTitle);

        imageViewBack.setOnClickListener(this);
        setFragment(new PhoneNumberFragment(), "Ваш телефон");

        //setFragment(new ProfileFragment(), "Проверка телефона");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                setFragment(fragmentBack, titleBack);
                break;
        }
    }

    @Override
    public void setFragment(Fragment fragment, String fragmentTitle) {
        switchTo(fragment);

        textViewTitle.setText(fragmentTitle);

        if (fragmentTitle.equals("Ваш телефон") || fragmentTitle.equals("Профиль")) {
            imageViewBack.setVisibility(View.GONE);
            fragmentBack = fragment;
            titleBack = fragmentTitle;
        } else {
            imageViewBack.setVisibility(View.VISIBLE);
        }
    }

    private void switchTo(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        setFragment(fragmentBack, titleBack);
    }
}
