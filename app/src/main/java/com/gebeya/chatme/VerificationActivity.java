package com.gebeya.chatme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    EditText phoneNumberInput;
    PinView pinView;
    TextView concatNo;
    FirebaseAuth mAuth;
    String codeSent;
    String verification_desc;
    String code;
    String editTextCode;
    String codeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();

        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        pinView = findViewById(R.id.firstPinView);
        concatNo = findViewById(R.id.display_numb);


        editTextCode = (String) pinView.getText().toString();
        codeInput = editTextCode;


        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        sendVerificationCode(phone);



        verification_desc = (String) concatNo.getText().toString();
        concatNo.setText(verification_desc + phone);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (codeInput.length() == 6) {

            verifySignCode(codeInput);
        }
        if (codeInput.isEmpty() || codeInput.length() < 6) {

            pinView.setError("Enter valid code");
            pinView.requestFocus();
            return;

        }


    }

    protected void sendVerificationCode(String phone) {
        if (phone.isEmpty() || phone.length() < 6) {
            phoneNumberInput.setError("Enter a valid mobile");
            phoneNumberInput.requestFocus();
            return;
        }

        PhoneAuthProvider auth = PhoneAuthProvider.getInstance();
        auth.verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks
        );        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                pinView.setText(code);
                //verifying the code
                verifySignCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);


            //storing the verification id that is sent to the user
            codeSent = s;

        }
    };

    private void verifySignCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = getIntent();
                            String phone = intent.getStringExtra("phone");
                            String phoneNumber = phone.substring(3);
                            String name = intent.getStringExtra("name");
                            Long phoneNo = Long.parseLong(phoneNumber);

                            //here you can open  activity
                            retrofit2.Call<ResponseBody> call = RetrofitClient
                                    .getInstance()
                                    .getUserServiceApi()
                                    .signUp(name,phoneNo);

                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        if (response.code() == 200) {

                                            String s = response.body().string();
                                            Intent i;


                                            i = new Intent(VerificationActivity.this, HomeActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                            startActivity(i);
                                        } else {

                                            String s = response.errorBody().string();
                                            Toast.makeText(VerificationActivity.this, s, Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    Toast.makeText(VerificationActivity.this, t.getMessage(), Toast.LENGTH_LONG);
                                }
                            });

                        } else {
                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Verification failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
