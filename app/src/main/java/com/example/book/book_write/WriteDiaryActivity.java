package com.example.book.book_write;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book.R;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WriteDiaryActivity extends Activity {

    private String bookTitle; // 책 제목을 받을 변수
    private static final String SERVER_URL = "http://192.168.33.198/save_diary.php"; // PHP 서버 주소

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writediary);

        // Intent로부터 책 제목을 받아옴
        Intent intent = getIntent();
        bookTitle = intent.getStringExtra("BOOK_TITLE");

        // 책 제목을 EditText에 설정
        EditText et_title = findViewById(R.id.edit_title);
        et_title.setText(bookTitle);  // 책 제목을 edit_title에 넣음
    }

    public void saveData(View v) {
        EditText et_title = findViewById(R.id.edit_title);
        String diary_title = et_title.getText().toString(); // 이미 설정된 책 제목이 그대로 사용됨

        EditText et_date = findViewById(R.id.edit_name);
        String diary_date = et_date.getText().toString();

        EditText et_content = findViewById(R.id.edit_dairy);
        String diary_content = et_content.getText().toString();

        // 서버에 데이터를 저장하는 작업을 비동기로 수행
        new SaveDiaryTask().execute(diary_title, diary_date, diary_content);
    }

    // 비동기 작업 클래스
    private class SaveDiaryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String diary_title = params[0];
            String diary_date = params[1];
            String diary_content = params[2];

            try {
                URL url = new URL(SERVER_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // POST 데이터 설정
                String postData = "diary_title=" + diary_title
                        + "&diary_date=" + diary_date
                        + "&diary_content=" + diary_content;

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postData.getBytes());
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Success";
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Success")) {
                // 성공적으로 저장되었을 때
                Toast.makeText(WriteDiaryActivity.this, "Diary saved successfully", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(WriteDiaryActivity.this, book_write_main.class);
                startActivity(it);
                finish();
            } else {
                // 오류 발생 시
                Toast.makeText(WriteDiaryActivity.this, "Error: " + result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
