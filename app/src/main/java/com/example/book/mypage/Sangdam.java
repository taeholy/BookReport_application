package com.example.book.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sangdam extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<String> messageList;
    private static final String TAG = "Sangdam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sangdam);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();
        adapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(adapter);

        loadMessages();

        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sangdam.this, gogaksenter.class);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });

        Button resetButton = findViewById(R.id.button2);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessages();
            }
        });
    }

    private void loadMessages() {
        SharedPreferences sharedPref = getSharedPreferences("SangdamMessages", Context.MODE_PRIVATE);
        Map<String, ?> allMessages = sharedPref.getAll();
        messageList.clear();
        for (Map.Entry<String, ?> entry : allMessages.entrySet()) {
            if (entry.getKey().startsWith("message_")) {
                messageList.add(entry.getValue().toString());
                Log.d(TAG, "Message loaded: " + entry.getKey() + " -> " + entry.getValue().toString()); // 로그 추가
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void clearMessages() {
        SharedPreferences sharedPref = getSharedPreferences("SangdamMessages", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        messageList.clear();
        adapter.notifyDataSetChanged();
        Log.d(TAG, "All messages cleared");
    }
}
