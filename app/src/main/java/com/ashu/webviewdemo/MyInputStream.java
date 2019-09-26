package com.ashu.webviewdemo;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
public class MyInputStream extends InputStream
{
    private final String mContentPath;
    private InputStream mInputStream;
    private final String TAG = "STREAMING_DEBUG";
    
    public MyInputStream(InputStream inputStream, String contentPath)
    {
        this.mContentPath = contentPath;
        this.mInputStream = inputStream;
    }
    
    @Override
    public int read() throws IOException
    {
        try
        {
            if(mContentPath != null && !mContentPath.contains("ttf"))
            {
                Thread.sleep(2000);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, "read: " + this.mContentPath);
        return mInputStream.read();
    }
    
    @Override
    public int read(@NonNull byte[] b) throws IOException
    {
        try
        {
            if(mContentPath != null && !mContentPath.contains("ttf"))
            {
                Thread.sleep(2000);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG, "    read: " + this.mContentPath);
        return mInputStream.read(b);
    }
    
    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException
    {
        try
        {
            if(mContentPath != null && !mContentPath.contains("ttf"))
            {
                Thread.sleep(2000);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        Log.d(TAG,"   read: " + this.mContentPath + "  offset: " + off + "   length:" + len);
        int result = mInputStream.read(b, off, len);
        return result;
    }
    
    @Override
    public int available() throws IOException
    {
        Log.d(TAG, "Input stream available" + " Content Path: " + mContentPath);
        return mInputStream.available();
    }
    
    @Override
    public void close() throws IOException
    {
        Log.d(TAG, "Input stream closed" + " Content Path: " + mContentPath);
        mInputStream.close();
    }
    
    @Override
    public boolean markSupported() {
        return mInputStream.markSupported();
    }
    
    @Override
    public long skip(long n) throws IOException
    {
        return mInputStream.skip(n);
    }
    
    @Override
    public synchronized void reset() throws IOException
    {
        mInputStream.reset();
    }
}
