package com.twitter.university.android.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class TweetActivity extends Activity implements View.OnClickListener, TextWatcher {

    private static final int MAX_TWEET_LENGTH = 140;
    private static final String TAG = "TweetActivity";
    YambaClient mYambaClient;
    TextView mTextRemaining;
    EditText mEditTweet;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        if (BuildConfig.DEBUG) Log.v(TAG, "onCreated() invoked");

        mEditTweet = (EditText) findViewById(R.id.edit_tweet);
        mEditTweet.addTextChangedListener(this);
        mTextRemaining = (TextView) findViewById(R.id.text_remaining);
        afterTextChanged(mEditTweet.getText());
        Button buttonTweet = (Button) findViewById(R.id.button_tweet);
        buttonTweet.setOnClickListener(this);
        mToast = Toast.makeText(this, null, Toast.LENGTH_LONG);

        mYambaClient = new YambaClient("student", "password");
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
                    new PostToTwitter().execute(msg);
                }
                break;
            default:
                // We should never get here!
                Log.w(TAG, "Unknown View clicked");
        }
    }

    private class PostToTwitter extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            int result = R.string.post_tweet_fail;
            if (params.length >= 1) {
                try {
                    mYambaClient.postStatus(params[0]);
                    result = R.string.post_tweet_success;
                } catch (YambaClientException e) {
                    Log.e(TAG, "Failed to post tweet", e);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            mToast.setText(result);
            mToast.show();
        }
    }
}
