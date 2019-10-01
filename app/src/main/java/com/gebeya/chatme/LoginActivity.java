package com.gebeya.chatme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumberInput;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumberInput= findViewById(R.id.phoneNumberInput);
        mAuth = FirebaseAuth.getInstance();

    }

    public void register_text(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void Login_button(View view) {

        sendVerificationCode();

        Intent i = new Intent(this, VertificationActivity.class);
        startActivity(i);
    }

    private void sendVerificationCode(){

        String phone = phoneNumberInput.getText().toString();

    }
}
