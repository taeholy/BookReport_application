package com.example.book.book_write;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.HomeActivity;
import com.example.book.R;
import com.example.book.categori.cate1;
import com.example.book.jjim.jjim;
import com.example.book.mypage.mypage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowMyData extends Activity {

    int nowData = 0;
    JSONArray dataArray;
    TextView date;
    TextView title;
    TextView t1;
    String diary_id;
    String diary_content;
    String diary_date;
    String diary_title;
    int numberOfData;
    private static final String SERVER_URL_LOAD = "http://192.168.33.224/load_diary.php";
    private static final String SERVER_URL_DELETE = "http://192.168.33.198/delete_diary.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        ImageButton categoryButton = findViewById(R.id.bar_category);
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyData.this, cate1.class);
                startActivity(intent);
            }
        });

        ImageButton homeButton = findViewById(R.id.bar_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyData.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton heartButton = findViewById(R.id.bar_heart);
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyData.this, jjim.class);
                startActivity(intent);
            }
        });

        ImageButton mypageButton = findViewById(R.id.bar_mypage);
        mypageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyData.this, mypage.class);
                startActivity(intent);
            }
        });

        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        t1 = findViewById(R.id.t1);

        loadData();
    }

    // 서버에서 데이터를 불러옴
    private void loadData() {
        new LoadDiaryTask().execute();
    }

    // 비동기적으로 데이터를 불러오는 작업
    private class LoadDiaryTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(SERVER_URL_LOAD);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();

            } catch (Exception e) {
                Log.e("LoadDiaryTask", "Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    dataArray = new JSONArray(result);
                    numberOfData = dataArray.length();
                    if (numberOfData > 0) {
                        showData(nowData);
                    }
                } catch (JSONException e) {
                    Log.e("LoadDiaryTask", "JSON Parsing error: " + e.getMessage());
                }
            }
        }
    }

    private void showData(int index) {
        try {
            JSONObject data = dataArray.getJSONObject(index);
            diary_id = data.getString("diary_id");
            diary_date = data.getString("diary_date");
            diary_title = data.getString("diary_title");
            diary_content = data.getString("diary_content");

            date.setText(diary_date);
            title.setText(diary_title);
            t1.setText(diary_content);
        } catch (JSONException e) {
            Log.e("showData", "JSON Exception: " + e.getMessage());
        }
    }

    public void nextData(View v) {
        if (nowData < numberOfData - 1) {
            nowData++;
            showData(nowData);
        }
    }

    public void previousData(View v) {
        if (nowData > 0) {
            nowData--;
            showData(nowData);
        }
    }

    public void deleteData(View v) {
        new DeleteDiaryTask().execute();
    }

    // 비동기적으로 데이터를 삭제하는 작업
    private class DeleteDiaryTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(SERVER_URL_DELETE);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // 현재 보여지는 데이터를 삭제
                JSONObject dataToDelete = dataArray.getJSONObject(nowData);
                String diary_id = dataToDelete.getString("diary_id");
                Log.d("DeleteDiaryTask", "diary_id to delete: " + diary_id); // 삭제할 diary_id 로그 확인
                String postData = "diary_id=" + diary_id;
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postData.getBytes());
                outputStream.flush();
                outputStream.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();

            } catch (Exception e) {
                Log.e("DeleteDiaryTask", "Exception: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.equals("Success")) {
                Toast.makeText(ShowMyData.this, "Diary deleted successfully", Toast.LENGTH_SHORT).show();
                removeDataFromArray(nowData);  // 삭제된 데이터를 배열에서 제거
                if (numberOfData > 0) {
                    nowData = (nowData > 0) ? nowData - 1 : 0;
                    showData(nowData);
                } else {
                    numberOfData = 0;
                    date.setText("");
                    title.setText("");
                    t1.setText("");
                }
            } else {
                Toast.makeText(ShowMyData.this, "Error deleting diary", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 삭제 후 배열에서 해당 데이터를 제거하는 메서드
    private void removeDataFromArray(int index) {
        JSONArray newArray = new JSONArray();
        try {
            for (int i = 0; i < dataArray.length(); i++) {
                if (i != index) {
                    newArray.put(dataArray.get(i));
                }
            }
            dataArray = newArray; // 업데이트된 배열로 교체
            numberOfData = dataArray.length();  // 데이터 수 갱신
        } catch (JSONException e) {
            Log.e("removeDataFromArray", "Error: " + e.getMessage());
        }
    }

    public void modifyData(View v) {
        Intent it = new Intent(this, ModifyMyData.class);
        it.putExtra("it_name", String.valueOf(nowData + 1));
        startActivity(it);
        finish();
    }
}
