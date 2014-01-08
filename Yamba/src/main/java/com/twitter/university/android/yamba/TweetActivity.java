package com.twitter.university.android.yamba;

import android.app.Activity;
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

public class TweetActivity extends Activity implements View.OnClickListener, TextWatcher {

    private static final int MAX_TWEET_LENGTH = 140;
    private static final String TAG = "TweetActivity";
    TextView mTextRemaining;
    EditText mEditTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mEditTweet = (EditText) findViewById(R.id.edit_tweet);
        mEditTweet.addTextChangedListener(this);
        mTextRemaining = (TextView) findViewById(R.id.text_remaining);
        afterTextChanged(mEditTweet.getText());
        Button buttonTweet = (Button) findViewById(R.id.button_tweet);
        buttonTweet.setOnClickListener(this);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Unimplemented
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Unimplemented
    }

    @Override
    public void afterTextChanged(Editable updated) {
        int remainingChars = MAX_TWEET_LENGTH - updated.length();
        mTextRemaining.setText(Integer.toString(remainingChars));
        if (remainingChars <= 0) {
            mTextRemaining.setTextColor(Color.RED);
        } else if (remainingChars <= 10) {
            mTextRemaining.setTextColor(Color.YELLOW);
        } else {
            mTextRemaining.setTextColor(Color.GREEN);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_tweet:
                // Handle the Tweet button click
                if (BuildConfig.DEBUG) Log.v(TAG, "Tweet button clicked");
                String msg = mEditTweet.getText().toString();
                if (BuildConfig.DEBUG) Log.v(TAG, "User entered: " + msg);
                mEditTweet.setText("");
                if (!TextUtils.isEmpty(msg)) {
                    Intent intent = new Intent(this, TweetService.class);
                    intent.putExtra(TweetService.EXTRA_TWEET_MSG, msg);
                    startService(intent);
                }
                break;
            default:
                // We should never get here!
                Log.w(TAG, "Unknown View clicked");
        }
    }
}
