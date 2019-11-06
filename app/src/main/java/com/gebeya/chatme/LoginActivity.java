package com.gebeya.chatme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




    }

    public void register_text(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void Login_button(View view) {

        EditText phoneNumberInput = findViewById(R.id.phoneNumberInput);
        String phone = phoneNumberInput.getText().toString().trim();
        //String phoneNumber = phone.substring(3);
        Long phoneNo = Long.parseLong(phone);
        retrofit2.Call<User> call = RetrofitClient
                .getInstance()
                .getUserServiceApi()
                .logIn(phoneNo);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.code() == 200) {

                        Intent i;

                       String token = response.body().getToken();

                         i = new Intent(LoginActivity.this, HomeActivity.class);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(i);


//                        Setting values in Preference:
// MY_PREFS_NAME - a static String variable like:
//public static final String MY_PREFS_NAME = "MyPrefsFile";
                        SharedPreferences.Editor editor = getSharedPreferences("chatme", MODE_PRIVATE).edit();
                        editor.putString("token", token);
                        editor.apply();
//                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                    } else {

                        String s = response.errorBody().string();
                        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG);
            }

        });

    }

//    private void getHeadersToken(){
//
//        retrofit2.Call<ResponseBody> call = RetrofitClient
//                .getInstance()
//                .getUserServiceApi()
//                .getTokenfromApi(token);
//        call.enqueue(new Callback<ResponseBody>()  {
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    if (response.code() == 200) {
//                        String s = response.body().string();
//                        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
//                    } else {
//
//                        String s = response.errorBody().string();
//                        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG);
//            }
//
//
//
//        });
//    }

}
