package com.example.qu;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
//        implements LoaderManager.LoaderCallbacks<Cursor>,
//        OnNewItemAddedListener {

    public final static String EXTRA_MESSAGE = "com.example.qu.MESSAGE";

    private ArrayAdapter<Quest> arrayAdapter;
    public ArrayList<Quest> quests;
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

        ListView listView = findViewById(R.id.questsListView);

        quests = new ArrayList<Quest>();
        quests.add(new Quest("Math Riddles", "Everywhere", new Task[] {
                new Task("4, 8, 16, ?", "32"),
                new Task("6 = 30\n3 = 15\n7 = 35\n2 = ?", "10"),
                new Task("4, 11, 18, ?", "25"),
                new Task("8 - 8 / 4 * 3 = ?", "2"),
                new Task("A + B = 60\nA - B = 40\nA / B = ?", "5"),
                new Task("7, 15, 31, ?", "63")
        }));
        quests.add(new Quest("Deathcore metal", "Lviv", new Task[] {
                new Task("1783, 3178, 8317, ?", "7831")
        }));
        quests.add(new Quest("Deathcore metal", "Kyiv", new Task[] {
                new Task("8 = 17\n22 = 45\n15 = 31\n20 = ?", "41"),
                new Task("6, 5 = 33\n7, 2 = 17\n11, 4 = 47\n3, 8 = ?", "27")
        }));

        arrayAdapter = new ArrayAdapter<Quest>(this, android.R.layout.simple_list_item_1, quests);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ListMenuItemView entry = (ListMenuItemView) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, CreateQuestActivity.class);
                intent.putExtra(EXTRA_MESSAGE, position);
                startActivity(intent);
            }
        });
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

//    public void onNewItemAdded(Quest newQuest) {
//        String msg = newQuest.title == null ? "От лажа" : newQuest.title;
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
//        quests.add(0, newQuest);
//        arrayAdapter.notifyDataSetChanged();
//
//        ContentResolver cr = getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put(QUContentProvider.KEY_TITLE, newQuest.title);
//
//        cr.insert(QUContentProvider.CONTENT_URI, values);
//        getSupportLoaderManager().initLoader(0, null, this);
//    }
}