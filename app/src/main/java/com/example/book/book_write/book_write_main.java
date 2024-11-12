package com.example.book.book_write;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.book.R;

public class book_write_main extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_write_xml);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;

        Intent intent = new Intent().setClass(this, ShowMyData.class);

        spec = tabHost.newTabSpec("show").setIndicator("독후감 보기").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, WriteDiaryActivity.class);
        spec = tabHost.newTabSpec("write").setIndicator("독후감 쓰기").setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }

}