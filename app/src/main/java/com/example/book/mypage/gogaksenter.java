package com.example.book.mypage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class gogaksenter extends AppCompatActivity {

    private RecyclerView recyclerView; // 기존 recyclerView 변수 이름 유지
    private MessageAdapter adapter;
    private List<String> messageList;
    private static final String TAG = "gogaksenter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gogaksenter);

        // recyclerView2로 ID 변경 반영
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();
        adapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(adapter);

        loadMessages();

        TextView noticeCountTextView = findViewById(R.id.notice_count);
        int messageCount = messageList.size();
        noticeCountTextView.setText("문의하신 내역은 총 " + messageCount + "건입니다.");

        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gogaksenter.this, mypage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        Button writeButton = findViewById(R.id.button2);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gogaksenter.this, Sangdam1n1.class);
                startActivity(intent);
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
                Log.d(TAG, "Message loaded: " + entry.getKey() + " -> " + entry.getValue().toString());
            }
        }
        adapter.notifyDataSetChanged();
    }
}
