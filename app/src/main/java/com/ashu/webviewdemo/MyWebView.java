package com.ashu.webviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
public class MyWebView extends WebView
{
    public MyWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing)
    {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public MyWebView(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
//        Log.d("STREAMING_DEBUG", "onDraw");
    }
    public void postVisualStateCallback (long requestId,
                                         WebView.VisualStateCallback callback)
    {
        super.postVisualStateCallback(requestId,callback);
//        Log.d("STREAMING_DEBUG", "postVisualStateCallback");
    }
}
