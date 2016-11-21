package com.group3.smartshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    private String message = "";
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        message = intent.getStringExtra(OnlineSearchActivity.EXTRA_MESSAGE);
        webView = (WebView) findViewById(R.id.activity_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(message);

    }
}
