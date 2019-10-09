package com.gebeya.chatme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import javax.microedition.khronos.egl.EGLDisplay;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    EditText phoneNumberInput;
    VerificationActivity VA = new VerificationActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);

    }
    public void register_button(View view) {


        String phone = (String)phoneNumberInput.getText().toString().trim();

        if(phone.isEmpty() || phone.length() < 4){
            phoneNumberInput.setError("Enter a valid mobile");
            phoneNumberInput.requestFocus();
            return;
        }



        Intent i = new Intent(this, VerificationActivity.class);
        i.putExtra("phone",phone);
        startActivity(i);

    }

    public void login_text(View view) {

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }


}
