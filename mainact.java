package com.datavtu.reseller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private String resellerUrl;

    private static final String DEFAULT_URL = "https://datavtu.ng";
    private static final String PREFS_NAME = "app_settings";
    private static final String PREF_RESELLER_URL = "reseller_url";
    private static final String PREF_RESELLER_CODE = "reseller_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);

        // Configure WebView
        configureWebView();

        // Get reseller URL
        resellerUrl = getResellerUrl();

        // Load URL
        webView.loadUrl(resellerUrl);
    }

    private void configureWebView() {

        WebSettings webSettings = webView.getSettings();

        // Enable JavaScript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Enable zoom
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // Enable viewport
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Cache settings
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);

        // Enable file access
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        // Mixed content mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(
                WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            );
        }

        // Set WebView client
        webView.setWebViewClient(new WebViewClientImpl(progressBar));

        // Set WebChromeClient for progress
        webView.setWebChromeClient(
            new android.webkit.WebChromeClient() {

                @Override
                public void onProgressChanged(
                        WebView view,
                        int newProgress
                ) {
                    super.onProgressChanged(view, newProgress);

                    progressBar.setProgress(newProgress);

                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        );
    }

    private String getResellerUrl() {
        return DEFAULT_URL;
    }
}
package com.datavtu.reseller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    private static final String PREFS_NAME = "ResellerPrefs";
    private static final String PREF_RESELLER_URL = "reseller_url";
    private static final String PREF_RESELLER_CODE = "reseller_code";

    private static final String DEFAULT_URL = "https://datavtu.ng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClientImpl(progressBar));

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress < 100) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });

        String url = getResellerUrl();
        webView.loadUrl(url);
    }

    private String getResellerUrl() {

        SharedPreferences prefs =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // First check saved URL
        String savedUrl =
                prefs.getString(PREF_RESELLER_URL, null);

        if (savedUrl != null && !savedUrl.isEmpty()) {
            return savedUrl;
        }

        // Check reseller parameter from deep link
        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null) {

            String resellerCode =
                    data.getQueryParameter("reseller");

            if (resellerCode != null &&
                    !resellerCode.isEmpty()) {

                String url =
                        "https://" + resellerCode + ".datavtu.ng";

                SharedPreferences.Editor editor = prefs.edit();

                editor.putString(PREF_RESELLER_URL, url);
                editor.putString(PREF_RESELLER_CODE, resellerCode);

                editor.apply();

                return url;
            }
        }

        // Check metadata
        try {

            String packageName = getPackageName();

            android.content.pm.ApplicationInfo appInfo =
                    getPackageManager().getApplicationInfo(
                            packageName,
                            android.content.pm.PackageManager.GET_META_DATA
                    );

            if (appInfo.metaData != null) {

                String resellerCode =
                        appInfo.metaData.getString("reseller_code");

                if (resellerCode != null &&
                        !resellerCode.isEmpty()) {

                    String url =
                            "https://" + resellerCode + ".datavtu.ng";

                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString(PREF_RESELLER_URL, url);
                    editor.putString(PREF_RESELLER_CODE, resellerCode);

                    editor.apply();

                    return url;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return DEFAULT_URL;
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.