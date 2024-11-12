package com.example.book.bookinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.book.R;
import com.example.book.book_write.WriteDiaryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class book_info extends AppCompatActivity {

    private boolean isHeartFilled = false;
    private ImageView imageView;
    private String bookId;
    private ImageButton heartButton;

    public static class ImageLoader {
        public static void load(final String url, final ImageView view) {
            ExecutorService executors = Executors.newSingleThreadExecutor();
            executors.execute(() -> {
                Bitmap image;
                try {
                    Log.d("ImageLoader", "Loading image from URL: " + url);
                    image = BitmapFactory.decodeStream(new URL(url).openStream());
                    final Bitmap finalImage = image;
                    view.post(() -> view.setImageBitmap(finalImage));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ImageLoader", "Error loading image: " + e.getMessage());
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        imageView = findViewById(R.id.book_img);
        heartButton = findViewById(R.id.heart_button);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("BOOK_ID");

        if (bookId != null) {
            new FetchBookDetailsTask().execute(bookId);
        }

        heartButton.setOnClickListener(v -> showJjimConfirmationDialog());

        ImageButton icN1Button = findViewById(R.id.ic_n1);
        icN1Button.setOnClickListener(v -> {
            showPopupWindow(v, "책을 누르면 PDF 뷰어로 이동합니다.");
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        Button buttButton = findViewById(R.id.butt);
        buttButton.setOnClickListener(v -> {
            TextView titleView = findViewById(R.id.tt2);
            String bookTitle = titleView.getText().toString();

            Intent intent1 = new Intent(book_info.this, WriteDiaryActivity.class);
            intent1.putExtra("BOOK_TITLE", bookTitle);
            startActivity(intent1);
        });

        // PDF Viewer triggered by ImageView click
        imageView.setOnClickListener(v -> openPdfViewer(bookId+".pdf"));
    }

    private void openPdfViewer(String fileName) {
        try {
            File pdfFile = getFileFromAssets(fileName);
            Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);

            Log.d("PDF_VIEWER", "PDF Uri: " + pdfUri.toString());

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("PDF_VIEWER", "Error opening PDF: " + e.getMessage());
            Toast.makeText(this, "Error opening PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private void showPopupWindow(View anchorView, String message) {
        // 팝업 윈도우 레이아웃 설정
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        // 팝업 윈도우 텍스트 설정
        TextView messageTextView = popupView.findViewById(R.id.popup_text);
        messageTextView.setText(message);

        // 팝업 윈도우 생성
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // 팝업 윈도우 외부 클릭 시 닫히게 설정
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // 팝업 윈도우 배경 설정
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        // 팝업 윈도우 위치 설정
        popupWindow.showAsDropDown(anchorView, 0, 0, Gravity.END);
    }

    private File getFileFromAssets(String fileName) throws IOException {
        InputStream inputStream = getAssets().open(fileName);
        File outFile = new File(getCacheDir(), fileName);
        FileOutputStream outputStream = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();

        return outFile;
    }

    private void showJjimConfirmationDialog() {
        String message = isHeartFilled ? "찜 삭제하시겠습니까?" : "찜 등록하시겠습니까?";
        new AlertDialog.Builder(this)
                .setTitle(isHeartFilled ? "찜 삭제" : "찜 등록")
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> {
                    isHeartFilled = !isHeartFilled;
                    heartButton.setImageResource(isHeartFilled ? R.drawable.heert : R.drawable.heart);
                    updateJjimStatus(isHeartFilled ? 1 : 0);
                    sendRefreshRequestToJjim();  // jjim 페이지에 새로고침 요청
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void sendRefreshRequestToJjim() {
        Intent intent = new Intent("REFRESH_JJIM");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);  // 브로드캐스트 송신
    }

    private void updateJjimStatus(int status) {
        if (bookId != null) {
            new UpdateJjimTask().execute(bookId, status);
        } else {
            Toast.makeText(this, "Error: Book ID is null", Toast.LENGTH_SHORT).show();
        }
    }

    private class UpdateJjimTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String bookId = (String) params[0];
            int status = (int) params[1];

            try {
                String urlString = "http://192.168.33.198/update_jjim.php";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String postData = "book_id=" + bookId + "&jjim=" + status;
                connection.getOutputStream().write(postData.getBytes());
                connection.getOutputStream().flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();

            } catch (Exception e) {
                Log.e("UpdateJjimTask", "Error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.equals("Success")) {
                Toast.makeText(book_info.this, "찜 상태 변경 완료", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(book_info.this, "Error updating jjim status", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class FetchBookDetailsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String bookId = params[0];
            String urlString = "http://192.168.33.198/getBookInfo.php?book_id=" + bookId;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("FetchBookDetailsTask", "Error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String title = jsonObject.getString("title");
                    String author = jsonObject.getString("author");
                    String genre = jsonObject.getString("genre");
                    String imgUrl = jsonObject.getString("img");
                    int jjim = jsonObject.getInt("jjim");

                    TextView titleView = findViewById(R.id.tt2);
                    TextView authorView = findViewById(R.id.tt3);
                    TextView genreView = findViewById(R.id.tt4);
                    ImageView imageView1 = findViewById(R.id.book_img);

                    titleView.setText(title);
                    authorView.setText(author);
                    genreView.setText(genre);
                    ImageLoader.load(imgUrl, imageView1);

                    isHeartFilled = (jjim == 1);
                    heartButton.setImageResource(isHeartFilled ? R.drawable.heert : R.drawable.heart);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(book_info.this, "Error parsing book details.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(book_info.this, "No book details received.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
