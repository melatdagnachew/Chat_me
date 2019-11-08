package com.gebeya.chatme.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gebeya.chatme.MessageAdapter;
import com.gebeya.chatme.model.MyAlarm;
import com.gebeya.chatme.R;
import com.gebeya.chatme.RetrofitClient;
import com.gebeya.chatme.model.Dialog;
import com.gebeya.chatme.model.Reminder;
import com.gebeya.chatme.model.ReminderRequest;
import com.gebeya.chatme.model.role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    TextView message, bottom_textView;
    EditText messageInput;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    ImageView catagory_icon, keyboard_icon, voice_icon, bot_icon;
    String name, greeting_message;
    ViewGroup viewGroup;
    LinearLayout bot_greeting;
    ArrayList<role> mRoleList;
    role responseMessage;
    Calendar calendar;
//    messageDatabase database;
//    messageDao messageDao;
    TextView showMessageBotTv;
    int hour;
    int minute;
    role menuItem;

    SharedPreferences prefs;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        messageInput = findViewById(R.id.message_Input);
        catagory_icon = findViewById(R.id.catagory_icon);
        keyboard_icon = findViewById(R.id.keyboard_icon);
        bottom_textView = findViewById(R.id.bottom_textView);
        voice_icon = findViewById(R.id.voice_icon);
        recyclerView = findViewById(R.id.recycler_view);
        showMessageBotTv = findViewById(R.id.show_message_bot);


        mRoleList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, mRoleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        String bot_message = "Hello" + " " + name + ", Welcome to chatMe.How may I help you today?";
        responseMessage = new role(bot_message, "", false);
        mRoleList.add(responseMessage);

        final String messagetoDisplay = intent.getStringExtra("message");

//        if (getSharedPreferences("chatme", MODE_PRIVATE) != null) {
            prefs = getSharedPreferences("chatme", MODE_PRIVATE);
            token = prefs.getString("token", "");//"No name defined" is the default value.
//        }

//        if (TextUtils.isEmpty(token)){
//            // todo log the user out
//            return;
//        }


        messageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                String message = messageInput.getText().toString();
                if (i == EditorInfo.IME_ACTION_SEND) {
                    displayUserMessage();
//                    messageDao.insertMessage(responseMessage);
                    messageInput.setText("");
                    messageAdapter.notifyDataSetChanged();


                    int num;
                    if (message.contains("Create a reminder")) {

                        createAReminder();
                    }
                    if (message.contains("Get my reminder")) {
                        getReminder();
                    }
                    if (message.contains("Cancle my reminder")) {
                        cancleReminder();
                    }

                }


                if (!isLastVisible())
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
//                    Toast.makeText(getApplicationContext(), "Sent!", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

//        database = Room.databaseBuilder(this, messageDatabase.class, "chatme_db.db")
//                .allowMainThreadQueries()
//                .build();

//        messageDao = database.getmessageDao();

    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }

    private void displayUserMessage() {
        responseMessage = new role(messageInput.getText().toString(), "", true);
        mRoleList.add(responseMessage);

    }

    private void createAReminder() {
//        if (token != null) {
//            Toast.makeText(this, "token is empty", Toast.LENGTH_SHORT).show();
//        }

        ReminderRequest reminderRequest = new ReminderRequest();
        Dialog dialog = new Dialog();
        dialog.setSpeech(messageInput.getText().toString().trim());
        reminderRequest.setDialog(dialog);

        retrofit2.Call<Reminder> call = RetrofitClient
                .getInstance()
                .getReminderServiceApi()
                .createReminder(reminderRequest, token);

        call.enqueue(new Callback<Reminder>() {
            @Override
            public void onResponse(Call<Reminder> call, Response<Reminder> response) {
                try {
                    if (response.code() == 200) {

                        Reminder s = response.body();

                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        //creating a new intent specifying the broadcast receiver
                        Intent i = new Intent(HomeActivity.this, MyAlarm.class);

                        //creating a pending intent using the intent
                        PendingIntent pi = PendingIntent.getBroadcast(HomeActivity.this, 0, i, 0);

                        //setting the repeating alarm that will be fired every day

                        Toast.makeText(HomeActivity.this, "alarm is set", Toast.LENGTH_SHORT).show();

                    } else {

                        String s = response.errorBody().string();
                        Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Reminder> call, Throwable t) {

                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void getReminder() {
        if (token != null) {
            Toast.makeText(this, "token is empty", Toast.LENGTH_SHORT).show();
        }

        final ReminderRequest reminderRequest = new ReminderRequest();


        retrofit2.Call<Reminder> call = RetrofitClient
                .getInstance()
                .getReminderServiceApi()
                .createReminder(reminderRequest, token);
        call.enqueue(new Callback<Reminder>() {
            @Override
            public void onResponse(Call<Reminder> call1, Response<Reminder> response) {
                try {
                    if (response.code() == 200) {
                        Reminder reminder = response.body();

                        String bot_message = "reminderRequest is set";
                        responseMessage = new role(reminder.getName(), reminder.getRemindTime(), false);
                        mRoleList.add(responseMessage);


                    } else {

                        String s = response.errorBody().string();
                        Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Reminder> call, Throwable t) {

                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }

    private void cancleReminder() {
        if (token != null) {
            Toast.makeText(this, "token is empty", Toast.LENGTH_SHORT).show();
        }

        ReminderRequest reminderRequest = new ReminderRequest();
        Dialog dialog = new Dialog();
        dialog.setSpeech(messageInput.getText().toString().trim());
        reminderRequest.setDialog(dialog);

        retrofit2.Call<Reminder> call = RetrofitClient
                .getInstance()
                .getReminderServiceApi()
                .createReminder(reminderRequest, token);

        call.enqueue(new Callback<Reminder>() {
            @Override
            public void onResponse(Call<Reminder> call2, Response<Reminder> response) {
                try {
                    if (response.code() == 200) {

                        Reminder s = response.body();

                        //setting the repeating alarm that will be fired every day

                        String bot_message = "reminderRequest is set";
//                        responseMessage = new role(bot_message, , false);
//                        mRoleList.add(responseMessage);

                    } else {

                        String s = response.errorBody().string();
                        Toast.makeText(HomeActivity.this, s, Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Reminder> call, Throwable t) {

                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG);
            }
        });

    }

    public void catagory_button(View view) {

        Intent intent = new Intent(HomeActivity.this, CatagoryActivity.class);
        startActivity(intent);
    }


    public void showKeyBoard(View view) {

        bottom_textView.setVisibility(View.INVISIBLE);
        catagory_icon.setVisibility(View.INVISIBLE);
        keyboard_icon.setVisibility(View.INVISIBLE);
        messageInput.setVisibility(View.VISIBLE);
        voice_icon.setVisibility(View.VISIBLE);
    }


    public void getSpeechInput(View view) {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(HomeActivity.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    messageInput.setText(result.get(0).toString());


                }
                break;
        }
    }


}
