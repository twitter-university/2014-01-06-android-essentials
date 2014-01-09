package com.twitter.university.android.yamba;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TweetActivity extends Activity implements TweetFragment.OnTweetCompletedListener {

    private static final String TAG = "TweetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        TweetFragment tweetFragment
                = (TweetFragment) getFragmentManager().findFragmentById(R.id.fragment_tweet);
        tweetFragment.setOnTweetCompletedListener(this);
    }

    @Override
    public void onTweetCompleted() {
        finish();
    }
}
