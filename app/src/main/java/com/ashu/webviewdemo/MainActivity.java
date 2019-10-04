package com.ashu.webviewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mWebView= findViewById(R.id.webView);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);

        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(new WebChromeClient());


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        WebView.setWebContentsDebuggingEnabled(true);
    }

    public void loadContent(View view) {
        mWebView.loadUrl("http://www.xyz.com/index.html");//file:///android_asset/index.html");
    }

    private class WebViewClientImpl extends WebViewClient {
        private Activity activity;

        public WebViewClientImpl(Activity activity) {
            this.activity = activity;
        }

        private String getMimeType(String path) {
            if (path != null && path.contains(".js")) {
                return "application/javascript";
            } else if (path != null && path.contains(".html")) {
                return "text/html";
            } else if (path != null && path.contains(".png")) {
                return "image/png";
            } else if (path != null && path.contains(".svg")) {
                return "image/svg+xml";
            } else if (path != null && path.contains(".css")) {
                return "text/css";
            } else if (path != null && path.contains(".ttf")) {
                return "font/ttf";
            }
            return "text/html";
        }

        private String urlToResourcePath(String url) {
            return url.replace("http://www.xyz.com/", "");
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Log.d(TAG, String.format("Intercepting Request to %s", request.getUrl()));
            try {
                String url = request.getUrl().toString();
                String resourcePath = urlToResourcePath(url);
                String mimeType = getMimeType(url);
                InputStream resource = openContent(resourcePath);
                if ("index.html".equals(resourcePath)) {
                    resource = new SlowInputStream(resource, resourcePath);

                }
                WebResourceResponse webResourceResponse = new WebResourceResponse(mimeType, "utf-8", resource);
                return webResourceResponse;
            } catch (Exception ex) {
                return buildErrorResponse();
            }
        }


        private InputStream openContent(String path) throws Exception {
            return getAssets().open(path);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return true;
        }

        private WebResourceResponse buildErrorResponse() {
            byte[] responseData = new byte[0];
            ByteArrayInputStream stream = new ByteArrayInputStream(responseData);
            return new WebResourceResponse("text/plain", "UTF-8", stream);
        }
    }
}
