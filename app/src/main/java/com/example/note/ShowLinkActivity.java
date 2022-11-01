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

public class ShowLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_link);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Web View");

        Intent gotIntent = getIntent();
        String url = gotIntent.getStringExtra("url");

        //Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);


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
            case R.id.next:
                Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.previous:
                Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show();
                return true;
        }



        return super.onOptionsItemSelected(item);
    }


    
    public void nextLink(View view){
        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
    }
    
    public void previousLink(View view){
        Toast.makeText(this, "Previous", Toast.LENGTH_SHORT).show();
    }
}