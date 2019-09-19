package com.gebeya.chatme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    public void register_button(View view) {

        Intent i = new Intent(this, VertificationActivity.class);
        startActivity(i);
    }
    public void login_text(View view) {

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}
