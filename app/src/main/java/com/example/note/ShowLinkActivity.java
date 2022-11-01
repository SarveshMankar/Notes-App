package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowLinkActivity extends AppCompatActivity {

    public int index = 0;
    public int total = 0;
    public ArrayList<String> myURLs;
    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_link);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Web View");

        Intent gotIntent = getIntent();
        String url = gotIntent.getStringExtra("url");

        String[] urls = url.split(System.lineSeparator());

        myURLs = new ArrayList<String>();

        for (String u : urls) {
            if (u.contains("http")) {
                myURLs.add(u);
            }
        }
        total = myURLs.size();

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(myURLs.get(0));

        Log.i("My URL", myURLs.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.showmenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.start:
                //Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                webView = findViewById(R.id.webView);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(myURLs.get(0));
                return true;

            case R.id.end:
                //Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                webView = findViewById(R.id.webView);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(myURLs.get(total - 1));
                return true;

            case R.id.next:
                //Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                if (index < total - 1) {
                    index++;
                    webView = findViewById(R.id.webView);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(myURLs.get(index));
                }
                return true;

            case R.id.previous:
                //Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show();
                if (index > 0) {
                    index--;
                    webView = findViewById(R.id.webView);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl(myURLs.get(index));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}