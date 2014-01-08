package com.twitter.university.android.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TweetStatusReceiver extends BroadcastReceiver {
    public TweetStatusReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean result = intent.getBooleanExtra(TweetService.EXTRA_TWEET_STATUS, false);
        Toast.makeText(
                context,
                result ? R.string.post_tweet_success : R.string.post_tweet_fail,
                Toast.LENGTH_LONG)
                     .show();
    }
}
