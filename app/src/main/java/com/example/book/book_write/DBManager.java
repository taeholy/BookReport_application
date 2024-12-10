package com.example.book.book_write;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DBManager {

<<<<<<< HEAD
    private static final String SERVER_URL = "http://192.168.33.224/save_diary.php";
=======
    private static final String SERVER_URL = "http://192.168.33.198/save_diary.php";
>>>>>>> 18ba15fb91e92b71360c675712fb20461f02d0e4

    public void saveDiary(String diaryTitle, String diaryDate, String diaryContent) {
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // 날짜도 그냥 String으로 처리 가능
            String postData = "diary_title=" + URLEncoder.encode(diaryTitle, "UTF-8") +
                    "&diary_date=" + URLEncoder.encode(diaryDate, "UTF-8") +  // diaryDate는 그대로 String
                    "&diary_content=" + URLEncoder.encode(diaryContent, "UTF-8");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postData.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 성공적으로 데이터 저장됨
            } else {
                // 오류 처리
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
