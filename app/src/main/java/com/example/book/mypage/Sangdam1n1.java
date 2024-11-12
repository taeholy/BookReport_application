package com.example.book.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class Sangdam1n1 extends AppCompatActivity {

    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sangdam1n1);

        messageEditText = findViewById(R.id.messageEditText);

        Button saveButton = findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessage();
                Intent intent = new Intent(Sangdam1n1.this, Sangdam.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });

        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveMessage() {
        String message = messageEditText.getText().toString();
        if (!message.isEmpty()) {
            SharedPreferences sharedPref = getSharedPreferences("SangdamMessages", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            // Add new message with unique key
            String key = "message_" + System.currentTimeMillis();
            editor.putString(key, message);
            editor.apply();
        }
    }
}
