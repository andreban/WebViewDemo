package com.ashu.webviewdemo;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

public class SlowInputStream extends InputStream {
    private final String mContentPath;
    private InputStream mInputStream;
    private final String TAG = "STREAMING_DEBUG";
    private long totalBytesRead = 0;

    public SlowInputStream(InputStream inputStream, String contentPath) {
        this.mContentPath = contentPath;
        this.mInputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        int value = mInputStream.read();
        if (value >= 0) totalBytesRead++;
        return value;
    }

    @Override
    public int read(@NonNull byte[] b) throws IOException {
        int read = mInputStream.read(b);
        if (read > 0) totalBytesRead += read;
        return read;
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) { }

        int read = mInputStream.read(b, off, len);
        if (read > 0) {
            totalBytesRead += read;
            Log.d(TAG, String.format("Content: %s", new String(b, 0, read)));
        }
        Log.d(TAG, String.format("Total Bytes Read: %d.", totalBytesRead));
        return read;
    }

    @Override
    public int available() throws IOException {
        Log.d(TAG, "Input stream available" + " Content Path: " + mContentPath);
        return mInputStream.available();
    }

    @Override
    public void close() throws IOException {
        Log.d(TAG, "Input stream closed" + " Content Path: " + mContentPath);
        mInputStream.close();
    }

    @Override
    public boolean markSupported() {
        return mInputStream.markSupported();
    }

    @Override
    public long skip(long n) throws IOException {
        return mInputStream.skip(n);
    }

    @Override
    public synchronized void reset() throws IOException {
        mInputStream.reset();
    }
}
