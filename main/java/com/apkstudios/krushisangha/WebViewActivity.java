package com.apkstudios.krushisangha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    //String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);




        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        String activity = bundle.getString("activity");
        toolbar = findViewById(R.id.toolbar_webview);
        if(activity.equals("market_price"))
            toolbar.setTitle(getString(R.string.market_price));
        else if(activity.equals("weather"))
            toolbar.setTitle(getString(R.string.weather));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        WebView browser = findViewById(R.id.web_view);
        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);;
        browser.loadUrl(url);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
