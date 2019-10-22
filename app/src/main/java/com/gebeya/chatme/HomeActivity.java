package com.gebeya.chatme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    TextView message,bottom_textView;
    EditText messageInput;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    ImageView catagory_icon,keyboard_icon,voice_icon,bot_icon;
    String name,greeting_message;
    ViewGroup viewGroup;
    LinearLayout bot_greeting;
    ArrayList<role> mRoleList;
    role responseMessage;
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

        mRoleList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, mRoleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        String bot_message = "Hello" + " " + name + ", Welcome to chatMe.How may I help you today?";
        responseMessage = new role(bot_message, false);
        mRoleList.add(responseMessage);


        messageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEND) {
                    responseMessage = new role(messageInput.getText().toString(), true);
                    mRoleList.add(responseMessage);
                    messageInput.setText("");
                    messageAdapter.notifyDataSetChanged();
                    if (!isLastVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
//                    Toast.makeText(getApplicationContext(), "Sent!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recyclerView.getAdapter().getItemCount();
        return (pos >= numItems);
    }


    public void catagory_button(View view) {

        Intent intent = new Intent(this,CatagoryActivity.class);
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
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    responseMessage = new role(result.get(0).toString(), true);
                    mRoleList.add(responseMessage);

                }
                break;
        }
    }

}
