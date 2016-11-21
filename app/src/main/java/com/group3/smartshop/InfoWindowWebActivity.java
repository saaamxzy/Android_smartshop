package com.group3.smartshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InfoWindowWebActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_window_web);

        WebView webViewMain = (WebView) findViewById(R.id.infowindow_webview);
        webViewMain.setWebViewClient(new WebViewClient());
        webViewMain.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();
        webViewMain.loadUrl(intent.getStringExtra(MapsActivity.URL_TO_VIEW));

    }
}
