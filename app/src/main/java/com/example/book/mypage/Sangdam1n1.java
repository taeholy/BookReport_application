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

        // 저장 버튼 클릭 시
        Button saveButton = findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessage();
                Intent intent = new Intent(Sangdam1n1.this, gogaksenter.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });

        // 뒤로가기 버튼
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

            // 고유 키로 메시지 저장
            String key = "message_" + System.currentTimeMillis();
            editor.putString(key, message);
            editor.apply();
        }
    }
}
