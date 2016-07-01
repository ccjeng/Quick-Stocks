package com.ccjeng.stock.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ccjeng.stock.R;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    @Bind(R.id.webview) WebView webView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.progress_wheel) ProgressWheel progressWheel;

    private String newsUrl;
    private String newsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = this.getIntent().getExtras();
        newsUrl = bundle.getString("newsUrl");
        newsTitle = bundle.getString("newsTitle");

        getSupportActionBar().setTitle(Html.fromHtml(newsTitle));

        setWebView();
    }

    private void setWebView() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressWheel.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressWheel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

        webView.loadUrl(newsUrl);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
