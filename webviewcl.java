package com.datavtu.reseller;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewClientImpl extends WebViewClient {

    private ProgressBar progressBar;

    public WebViewClientImpl(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onPageStarted(
            WebView view,
            String url,
            Bitmap favicon
    ) {

        super.onPageStarted(view, url, favicon);

        if (progressBar != null) {
            progressBar.setVisibility(android.view.View.VISIBLE);
        }
    }

    @Override
    public void onPageFinished(
            WebView view,
            String url
    ) {

        super.onPageFinished(view, url);

        if (progressBar != null) {
            progressBar.setVisibility(android.view.View.GONE);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(
            WebView view,
            WebResourceRequest request
    ) {

        // Allow all URLs
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(
            WebView view,
            String url
    ) {

        // Load URL inside WebView
        view.loadUrl(url);

        return true;
    }
}