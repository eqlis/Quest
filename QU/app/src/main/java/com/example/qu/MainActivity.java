package com.example.qu;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        OnNewItemAddedListener {

    private ArrayAdapter arrayAdapter;
    private ArrayList<Quest> quests;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreateQuestActivity.class);
                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();
        QuestsFragment questsFragment = (QuestsFragment) fragmentManager.findFragmentById(R.id.questsFragment);

        quests = new ArrayList<Quest>();
        arrayAdapter = new ArrayAdapter(this, R.layout.quests_list_item, quests);

        questsFragment.setListAdapter(arrayAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this, QUContentProvider.CONTENT_URI,
                null, null, null, null);
        return loader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int keyQuestIndex = cursor.getColumnIndexOrThrow(QUContentProvider.KEY_TITLE);

        quests.clear();
        while (cursor.moveToNext()) {
            Quest newItem = new Quest(cursor.getString(keyQuestIndex));
            quests.add(newItem);
        }

        arrayAdapter.notifyDataSetChanged();
    }

    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onNewItemAdded(Quest newQuest) {
        quests.add(0, newQuest);
        arrayAdapter.notifyDataSetChanged();

        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(QUContentProvider.KEY_TITLE, newQuest.title);

        cr.insert(QUContentProvider.CONTENT_URI, values);
        getSupportLoaderManager().initLoader(0, null, this);
    }
}