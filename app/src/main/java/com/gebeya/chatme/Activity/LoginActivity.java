package com.gebeya.chatme.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gebeya.chatme.R;
import com.gebeya.chatme.RetrofitClient;
import com.gebeya.chatme.model.LoginResponse;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static String token;
    SharedPreferences.Editor editor;

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
//        Long phoneNo = Long.parseLong(phone);
//        retrofit2.Call<LoginResponse> call = RetrofitClient
//                .getInstance()
//                .getUserServiceApi()
//                .logIn(phoneNo);



        String token = "ldakjflkasjfdlkasjflksa;jfksaj;";//response.body().getToken();

        editor = getSharedPreferences("chatme", MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.apply();

        Intent i = new Intent(LoginActivity.this, HomeActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);

//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                try {
//                    if (response.code() == 200) {
//
//
//                        if (response.body()!=null && response.body().getToken()!=null){
//                            String token = "ldakjflkasjfdlkasjflksa;jfksaj;";//response.body().getToken();
//
//                            editor = getSharedPreferences("chatme", MODE_PRIVATE).edit();
//                            editor.putString("token", token);
//                            editor.apply();
//
//                        }
//
//
////                        Setting values in Preference:
//// MY_PREFS_NAME - a static String variable like:
////public static final String MY_PREFS_NAME = "MyPrefsFile";
//
////                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
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
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG);
//            }
//
//        });

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
