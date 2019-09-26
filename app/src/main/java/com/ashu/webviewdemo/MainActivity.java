package com.ashu.webviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        MyWebView webView = findViewById(R.id.webView);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);

        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new WebChromeClient());


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        WebView.setWebContentsDebuggingEnabled(true);
        webView.loadUrl("http://www.xyz.com/index.html");//file:///android_asset/index.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class WebViewClientImpl extends WebViewClient
    {
        private Activity activity = null;

        public WebViewClientImpl(Activity activity)
        {
            this.activity = activity;
        }

        private String getMimeType(String path)
        {
            if (path != null && path.contains(".js"))
            {
                return "application/javascript";
            }
            else if(path != null && path.contains(".html"))
            {
                return "text/html";
            }

            else if(path != null && path.contains(".png"))
            {
                return "image/png";
            }

            else if(path != null && path.contains(".svg"))
            {
                return "image/svg+xml";
            }

            else if(path != null && path.contains(".css"))
            {
                return "text/css";
            }

            else if(path != null && path.contains(".ttf"))
            {
                return "font/ttf";
            }
            return "text/html";
        }

        private String urlToResourcePath(String url)
        {
            return url.replace("http://www.xyz.com/", "");
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url)
        {

            try
            {
                String resourcePath = urlToResourcePath(url);
                String mimeType = getMimeType(url);
                InputStream resource = openContent(resourcePath);
                MyInputStream myInputStream = new MyInputStream(resource,resourcePath);
                return new WebResourceResponse(mimeType, null, myInputStream);
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
            return buildErrorResponse();
        }

        private InputStream openContent(String path) throws Exception
        {
            return  getAssets().open(path);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url)
        {
            return true;
        }

        private WebResourceResponse buildErrorResponse()
        {
            byte[] responseData = new byte[0];
            ByteArrayInputStream stream = new ByteArrayInputStream(responseData);
            return new WebResourceResponse("text/plain", "UTF-8", stream);
        }
    }
}
