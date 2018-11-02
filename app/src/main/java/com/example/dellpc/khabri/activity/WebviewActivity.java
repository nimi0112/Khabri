package com.example.dellpc.khabri.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dellpc.khabri.R;
import com.example.dellpc.khabri.utils.Utility;

public class WebviewActivity extends AppCompatActivity {

    private String urltoload,title;
    private WebView webViewWiki;
    private Toolbar toolbar;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        toolbar = (Toolbar) findViewById(R.id.toolbar_wiki);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        webViewWiki = (WebView) findViewById(R.id.webView_wiki);
        Intent intent = getIntent();
        urltoload = intent.getStringExtra("urltoload");
        title=intent.getStringExtra("title");
        getSupportActionBar().setTitle(title);

        mProgressDialog = Utility.showLoadingDialog(this);

        webViewWiki.getSettings().setJavaScriptEnabled(true);

        webViewWiki.setHorizontalScrollBarEnabled(false);
        initWebViewWikipedia();

        webViewWiki.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.cancel();
                }

            }
        });


        webViewWiki.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }

                }

                return false;
            }

        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void initWebViewWikipedia() {
        webViewWiki.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webViewWiki.loadUrl(request.getUrl().toString());
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        webViewWiki.loadUrl(urltoload);
        webViewWiki.clearCache(true);
        webViewWiki.clearHistory();
        webViewWiki.getSettings().setJavaScriptEnabled(true);
        webViewWiki.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
