package com.ccjeng.stock.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ccjeng.stock.R;
import com.ccjeng.stock.view.base.BaseActivity;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsActivity extends BaseActivity {

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
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.loadUrl(newsUrl);
        }

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                progressWheel.setProgress(progress * 100);
                if (progress > 50) {
                    progressWheel.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressWheel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });


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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }
}
