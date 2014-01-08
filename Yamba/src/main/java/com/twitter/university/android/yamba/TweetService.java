package com.twitter.university.android.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class TweetService extends IntentService {
    public static final String EXTRA_TWEET_MSG = "EXTRA_TWEET_MSG";
    public static final String EXTRA_TWEET_STATUS = "EXTRA_TWEET_STATUS";
    public static final String ACTION_TWEET_STATUS
            = "com.twitter.university.android.ACTION_TWEET_STATUS";
    public static final String PERMISSION_TWEET_NOTIFY
            = "com.twitter.university.android.yamba.permission.NEW_TWEET_NOTICE";

    private static final String TAG = "TweetService";
    private YambaClient mYambaClient;

    public TweetService() {
        // Identify the worker thread for debugging purposes
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mYambaClient = new YambaClient("student", "password");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra(TweetService.EXTRA_TWEET_MSG);
        boolean status = false;
        if (!TextUtils.isEmpty(msg)) {
            try {
                mYambaClient.postStatus(msg);
                status = true;
                Log.d(TAG, "Successfully posted tweet: " + msg);
            } catch (YambaClientException e) {
                Log.e(TAG, "Failed to post tweet", e);
            }
        }
        Intent broadcastIntent = new Intent(TweetService.ACTION_TWEET_STATUS);
        broadcastIntent.putExtra(TweetService.EXTRA_TWEET_STATUS, status);
        sendBroadcast(broadcastIntent, TweetService.PERMISSION_TWEET_NOTIFY);
    }
}
