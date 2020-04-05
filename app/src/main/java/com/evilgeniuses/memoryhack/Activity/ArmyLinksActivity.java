package com.evilgeniuses.memoryhack.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.evilgeniuses.memoryhack.R;

public class ArmyLinksActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LINK_1 = "http://mil.ru/";
    private static final String LINK_2 = "https://www.moypolk.ru/login/";
    private static final String LINK_3 = "https://polkrf.ru/poisk-veterana/veteran/search/";
    private static final String LINK_4 = "http://pomnimvseh.histrf.ru/personal/my/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_army_links);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnLink1).setOnClickListener(this);
        findViewById(R.id.btnLink2).setOnClickListener(this);
        findViewById(R.id.btnLink3).setOnClickListener(this);
        findViewById(R.id.btnLink4).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnLink1:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_1));
                break;
            case R.id.btnLink2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_2));
                break;
            case R.id.btnLink3:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_3));
                break;
            case R.id.btnLink4:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_4));
                break;
        }

        startActivity(intent);
    }
}
