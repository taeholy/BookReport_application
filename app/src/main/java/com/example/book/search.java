package com.example.book;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class search extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private LinearLayout recentSearchesLayout;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "RecentSearches";
    private static final String KEY_SEARCHES = "searches";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        editTextSearch = findViewById(R.id.txt_search);
        buttonSearch = findViewById(R.id.buttonSearch);
        recentSearchesLayout = findViewById(R.id.recent_searches_layout);
        ImageButton backButton = findViewById(R.id.back_button);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // 뒤로가기 버튼 클릭 리스너
        backButton.setOnClickListener(v -> finish());

        // 검색 버튼 클릭 리스너
        buttonSearch.setOnClickListener(view -> {
            String searchQuery = editTextSearch.getText().toString().trim();

            if (!searchQuery.isEmpty()) {
                saveSearchQuery(searchQuery);
                Intent intent = new Intent(search.this, search_Result.class);
                intent.putExtra("searchQuery", searchQuery);
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
            Intent intent = new Intent(search.this, search_Result.class);
            intent.putExtra("searchQuery", query);
            startActivity(intent);
        });

        recentSearchesLayout.addView(button, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
    }
    public void openWebPage(View view) {
        String url = "https://www.yes24.com/Product/Goods/91065309"; // 이동할 웹사이트 URL
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}

