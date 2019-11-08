package com.gebeya.chatme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gebeya.chatme.R;

public class CatagoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);
    }

    public void back_to_home(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
