package com.example.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.book.book_write.book_write_main;
import com.example.book.categori.cate1;
import com.example.book.jjim.jjim;
import com.example.book.mypage.mypage;
import com.example.book.bookinfo.book_info;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class search_Result extends AppCompatActivity {

    private static final String SEARCH_URL = "http://192.168.33.224/search.php?query=";


    private EditText editTextSearch;
    private Button buttonSearch;
    private List<TextView> titles = new ArrayList<>();
    private List<TextView> authors = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private LinearLayout recentSearchesLayout;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "RecentSearches";
    private static final String KEY_SEARCHES = "searches";

    public static class ImageLoader {
        public static void load(final String url, final ImageView view) {
            ExecutorService executors = Executors.newSingleThreadExecutor();
            executors.execute(() -> {
                Bitmap image;
                try {
                    image = BitmapFactory.decodeStream(new URL(url).openStream());
                    final Bitmap finalImage = image;
                    view.post(() -> view.setImageBitmap(finalImage));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        ImageButton categoryButton = findViewById(R.id.bar_category);
        categoryButton.setOnClickListener(v -> {
            // cate1 Activity로 이동
            Intent intent = new Intent(search_Result.this, cate1.class);
            startActivity(intent);
        });

        ImageButton dookreadingButton = findViewById(R.id.bar_dookreading);
        dookreadingButton.setOnClickListener(v -> {
            // cate1 Activity로 이동
            Intent intent = new Intent(search_Result.this, book_write_main.class);
            startActivity(intent);
        });

        ImageButton HomeButton = findViewById(R.id.bar_home);
        HomeButton.setOnClickListener(v -> {
            // jjim Activity로 이동
            Intent intent = new Intent(search_Result.this, HomeActivity.class);
            startActivity(intent);
        });

        // Heart 버튼 클릭 이벤트 설정
        ImageButton heartButton = findViewById(R.id.bar_heart);
        heartButton.setOnClickListener(v -> {
            // jjim Activity로 이동
            Intent intent = new Intent(search_Result.this, jjim.class);
            startActivity(intent);
        });

        ImageButton mypageButton = findViewById(R.id.bar_mypage);
        mypageButton.setOnClickListener(v -> {
            // jjim Activity로 이동
            Intent intent = new Intent(search_Result.this, mypage.class);
            startActivity(intent);
        });

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        recentSearchesLayout = findViewById(R.id.recent_searches_layout);

        // TextView들을 리스트에 추가
        titles.add(findViewById(R.id.title1));
        titles.add(findViewById(R.id.title2));
        titles.add(findViewById(R.id.title3));
        titles.add(findViewById(R.id.title4));
        titles.add(findViewById(R.id.title5));
        titles.add(findViewById(R.id.title6));

        authors.add(findViewById(R.id.author1));
        authors.add(findViewById(R.id.author2));
        authors.add(findViewById(R.id.author3));
        authors.add(findViewById(R.id.author4));
        authors.add(findViewById(R.id.author5));
        authors.add(findViewById(R.id.author6));

        // 이미지뷰들을 리스트에 추가
        imageViews.add(findViewById(R.id.img1));
        imageViews.add(findViewById(R.id.img2));
        imageViews.add(findViewById(R.id.img3));
        imageViews.add(findViewById(R.id.img4));
        imageViews.add(findViewById(R.id.img5));
        imageViews.add(findViewById(R.id.img6));

        editTextSearch = findViewById(R.id.txt_search);
        buttonSearch = findViewById(R.id.buttonSearch);

        // 검색어 가져오기
        String searchQuery = getIntent().getStringExtra("searchQuery");
        TextView explainTextView = findViewById(R.id.explain);
        String explainText = "\"" + searchQuery + "\" 에 대한 검색 내용입니다.";
        explainTextView.setText(explainText);
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchBooks(searchQuery);
        }

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        buttonSearch.setOnClickListener(view -> {
            String searchQuery2 = editTextSearch.getText().toString().trim();

            if (!searchQuery2.isEmpty()) {
                saveSearchQuery(searchQuery2);
                Intent intent = new Intent(search_Result.this, search_Result.class);
                intent.putExtra("searchQuery", searchQuery2);
                startActivity(intent);
            }
        });


        loadRecentSearches();

    }


    private void saveSearchQuery(String query) {
        Set<String> searches = sharedPreferences.getStringSet(KEY_SEARCHES, new HashSet<>());
        searches.add(query);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_SEARCHES, searches);
        editor.apply();

        addSearchQueryToLayout(query);
    }

    private void searchBooks(String searchQuery) {
        String url = SEARCH_URL + searchQuery;

        // Volley를 사용하여 서버에 요청을 보냄
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        // 서버로부터 받은 JSON 배열을 순회하며 각 항목을 TextView와 ImageView에 설정
                        int jsonArrayLength = jsonArray.length();
                        for (int i = 0; i < jsonArrayLength; i++) {
                            JSONObject bookObject = jsonArray.getJSONObject(i);
                            titles.get(i).setText(bookObject.getString("title"));
                            authors.get(i).setText(bookObject.getString("author"));
                            String imgUrl = bookObject.getString("img");
                            String bookId = bookObject.getString("book_id");

                            // 각 이미지뷰에 이미지를 로드합니다.
                            ImageLoader.load(imgUrl, imageViews.get(i));

                            // 이미지뷰에 클릭 리스너 설정
                            imageViews.get(i).setOnClickListener(view -> {
                                Intent intent = new Intent(search_Result.this, book_info.class);
                                intent.putExtra("BOOK_ID", bookId);
                                startActivity(intent);
                            });
                        }

                        // 데이터가 없는 경우 나머지 TextView와 ImageView를 비웁니다.
                        for (int i = jsonArrayLength; i < titles.size(); i++) {
                            titles.get(i).setText("");
                            authors.get(i).setText("");
                            imageViews.get(i).setImageDrawable(null);
                            imageViews.get(i).setOnClickListener(null); // 클릭 리스너 제거
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley Error", error.toString()));
        queue.add(stringRequest);
    }

    private void loadRecentSearches() {
        Set<String> searches = sharedPreferences.getStringSet(KEY_SEARCHES, new HashSet<>());
        for (String query : searches) {
            addSearchQueryToLayout(query);
        }
    }

    private void addSearchQueryToLayout(String query) {
        Button button = new Button(this);
        button.setText(query);
        button.setTextSize(14);
        button.setPadding(8, 8, 8, 8);
        button.setBackgroundResource(R.drawable.rounded_button); // 배경 설정
        button.setOnClickListener(view -> {
            Intent intent = new Intent(search_Result.this, search_Result.class);
            intent.putExtra("searchQuery", query);
            startActivity(intent);
        });

        recentSearchesLayout.addView(button, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
    }
}
