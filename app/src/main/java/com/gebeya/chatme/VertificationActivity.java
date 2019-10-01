package com.gebeya.chatme;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class VertificationActivity extends AppCompatActivity {

    EditText firstChar,secondChar,thirdChar,fourthChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        firstChar = findViewById(R.id.firstCodeChar);
        secondChar= findViewById(R.id.firstCodeChar);
        thirdChar=findViewById(R.id.thirdCodeChar);
        fourthChar=findViewById(R.id.fourthCodeChar);
    }

}
