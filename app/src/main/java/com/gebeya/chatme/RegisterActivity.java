package com.gebeya.chatme;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import javax.microedition.khronos.egl.EGLDisplay;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    EditText phoneNumberInput,nameInput;
    VerificationActivity VA = new VerificationActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        nameInput = findViewById(R.id.nameInput);
    }
    public void register_button(View view) {

        String name= nameInput.getText().toString();
        String phone = (String)phoneNumberInput.getText().toString().trim();

        if(name.isEmpty()){
            nameInput.setError("Enter a valid mobile");
            nameInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    int length = charSequence.toString().trim().length();
                    if (length > 0) {
                        // Change the icon to send
                    } else {
                        // Change the icon to microphone
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            nameInput.requestFocus();
            return;
        }
        if(phone.isEmpty() || phone.length() < 4){
            phoneNumberInput.setError("Enter a valid mobile");
            phoneNumberInput.requestFocus();
            return;
        }

        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("phone",phone);
        i.putExtra("name",name);
        startActivity(i);

    }

    public void login_text(View view) {

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }


}
