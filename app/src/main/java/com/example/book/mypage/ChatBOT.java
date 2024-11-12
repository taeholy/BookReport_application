package com.example.book.mypage;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

import java.util.ArrayList;
import java.util.List;

public class ChatBOT extends AppCompatActivity {

    private ListView chatListView;
    private EditText chatInput;
    private Button sendButton;
    private ArrayAdapter<String> chatAdapter;
    private List<String> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_bot);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        chatListView = findViewById(R.id.chat_list_view);
        chatInput = findViewById(R.id.chat_input);
        sendButton = findViewById(R.id.send_button);

        Button buttonGreeting = findViewById(R.id.button_greeting);
        Button buttonInfoUpdate = findViewById(R.id.button_info_update);
        Button buttonReport = findViewById(R.id.button_report);
        Button buttonGrade = findViewById(R.id.button_grade);

        chatMessages = new ArrayList<>();
        chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        chatListView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = chatInput.getText().toString();
                if (!userMessage.isEmpty()) {
                    handleUserMessage(userMessage);
                    chatInput.setText("");
                }
            }
        });

        buttonGreeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserMessage("안녕하세요");
            }
        });

        buttonInfoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserMessage("회원정보 수정은 어디서 하나요");
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserMessage("독후감 작성은 어떻게 해야하나요");
            }
        });

        buttonGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserMessage("엑스포는 재밌을까요?");
            }
        });
    }

    private void handleUserMessage(String userMessage) {
        chatMessages.add("나: " + userMessage);
        chatAdapter.notifyDataSetChanged();

        // Simple response logic
        String botResponse = getBotResponse(userMessage);
        chatMessages.add("챗봇: " + botResponse);
        chatAdapter.notifyDataSetChanged();
    }

    private String getBotResponse(String message) {
        switch (message.toLowerCase()) {
            case "안녕하세요":
                return "안녕하세요! 저는 챗봇입니다!";
            case "회원정보 수정은 어디서 하나요":
                return "회원정보 수정은 메뉴 우측, 마이페이지에서 수정 가능합니다";
            case "독후감 작성은 어떻게 해야하나요":
                return "독후감 작성은 메뉴 좌측, 독후감페이지에서 작성 가능합니다";
            case "엑스포는 재밌을까요?":
                return "당연하죠! 학생들의 작품을 마음껏 구경하세요!";
            default:
                return "추후 추가될 답변입니다";
        }
    }
}
