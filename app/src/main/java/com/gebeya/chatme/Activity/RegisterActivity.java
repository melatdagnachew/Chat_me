package com.gebeya.chatme.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.gebeya.chatme.R;
import com.gebeya.chatme.RetrofitClient;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;


public class RegisterActivity extends AppCompatActivity {

    EditText phoneNumberInput, nameInput;
    VerificationActivity VA = new VerificationActivity();
    String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        nameInput = findViewById(R.id.nameInput);

        name = nameInput.getText().toString();
        phone = phoneNumberInput.getText().toString().trim();

    }

    public void register_button(View view) {


        String name = nameInput.getText().toString();
        String phone = phoneNumberInput.getText().toString().trim();

        Long phoneNo = Long.parseLong(phone);

        if (name.isEmpty()) {
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
        if (phone.isEmpty() || phone.length() < 4) {
            phoneNumberInput.setError("Enter a valid mobile");
            phoneNumberInput.requestFocus();
            return;
        }


        phoneNo = Long.parseLong(phone);
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getUserServiceApi()
                .signUp(name, phoneNo);

        final Intent logInt = new Intent(this, VerificationActivity.class);

        logInt.putExtra("phone", phone);
        logInt.putExtra("name", name);

//        Intent i = new Intent(this, VerificationActivity.class);
//        i.putExtra("phone",phone);
//        i.putExtra("name",name);
//        startActivity(i);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    if (response.code() == 200) {
//
//
//                        startActivity(logInt);
//                    } else {
//
//                        String s = response.errorBody().string();
//                        Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG);
//            }
//        });


    }


    public void login_text(View view) {

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }


}
