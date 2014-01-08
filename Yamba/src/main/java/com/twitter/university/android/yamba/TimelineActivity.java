package com.twitter.university.android.yamba;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.twitter.university.android.yamba.service.YambaContract;

public class TimelineActivity extends Activity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String[] FROM = {
            YambaContract.Timeline.Columns.HANDLE,
            YambaContract.Timeline.Columns.TIMESTAMP,
            YambaContract.Timeline.Columns.TWEET,
    };
    private static final int[] TO = {
            R.id.text_user,
            R.id.text_date,
            R.id.text_tweet,
    };
    private static final int TIMELINE_LOADER = 42;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);

        mAdapter = new SimpleCursorAdapter(
                this,
                R.layout.timeline_row,
                null,
                FROM,
                TO,
                0
        );

        ListView listTimeline = (ListView) findViewById(R.id.list_timeline);
        listTimeline.setAdapter(mAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getApplicationContext(),
                YambaContract.Timeline.URI,
                null,
                null,
                null,
                YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
