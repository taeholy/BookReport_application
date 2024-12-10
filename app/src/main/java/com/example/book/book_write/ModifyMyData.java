package com.example.book.book_write;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.book.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ModifyMyData extends Activity {

    TextView date;
    EditText title;
    EditText t1;
    String diary_date;
    String diary_title;
    String diary_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);

        date = findViewById(R.id.date);
        title = findViewById(R.id.edit_title);
        t1 = findViewById(R.id.t1);

        Intent it = getIntent();
        String str_name = it.getStringExtra("it_name");
        int nowData = Integer.parseInt(str_name) - 1;

        loadDataAndModify(nowData);
    }

    private void loadDataAndModify(int id) {
        // 데이터 로드 및 수정 스레드
        new Thread(() -> {
            try {
                // 1. 데이터 로드
                String loadUrl = "http://192.168.33.224/load_data.php?id=" + id;
                URL url = new URL(loadUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(result.toString());

                // 데이터 로드 성공 시
                if (jsonResponse.has("error")) {
                    Log.e("ModifyMyData", "Error: " + jsonResponse.getString("error"));
                } else {
                    diary_date = jsonResponse.getString("diary_date");
                    diary_title = jsonResponse.getString("diary_title");
                    diary_content = jsonResponse.getString("diary_content");

                    // UI 스레드에서 UI 업데이트
                    runOnUiThread(() -> {
                        date.setText(diary_date);
                        title.setText(diary_title);
                        t1.setText(diary_content);
                    });
                }

                // 2. 데이터 수정
                findViewById(R.id.modify).setOnClickListener(v -> {
                    new Thread(() -> {
                        try {
                            String modifyUrl = "http://192.168.33.198/modify_data.php";
                            URL modifyRequestUrl = new URL(modifyUrl);
                            HttpURLConnection modifyConn = (HttpURLConnection) modifyRequestUrl.openConnection();
                            modifyConn.setRequestMethod("POST");
                            modifyConn.setDoOutput(true);

                            String new_title = title.getText().toString();
                            String new_content = t1.getText().toString();

                            String postData = "diary_date=" + URLEncoder.encode(diary_date, "UTF-8") +
                                    "&diary_title=" + URLEncoder.encode(new_title, "UTF-8") +
                                    "&diary_content=" + URLEncoder.encode(new_content, "UTF-8");

                            OutputStream os = modifyConn.getOutputStream();
                            os.write(postData.getBytes());
                            os.flush();
                            os.close();

                            BufferedReader modifyReader = new BufferedReader(new InputStreamReader(modifyConn.getInputStream()));
                            StringBuilder modifyResult = new StringBuilder();
                            String modifyLine;
                            while ((modifyLine = modifyReader.readLine()) != null) {
                                modifyResult.append(modifyLine);
                            }
                            modifyReader.close();

                            JSONObject modifyResponse = new JSONObject(modifyResult.toString());

                            // 수정 완료 확인
                            if (modifyResponse.has("success")) {
                                Log.i("ModifyMyData", "Data updated successfully");
                                // 메인 화면으로 이동
                                Intent it = new Intent(ModifyMyData.this, book_write_main.class);
                                startActivity(it);
                                finish();
                            } else {
                                Log.e("ModifyMyData", "Error: " + modifyResponse.getString("error"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 취소 버튼 처리
    public void cancelData(View v) {
        Intent it = new Intent(this, book_write_main.class);
        startActivity(it);
        finish();
    }
}
