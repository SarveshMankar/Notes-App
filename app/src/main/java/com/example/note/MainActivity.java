package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mAddNoteFab, mAddLinkFab;
    ExtendedFloatingActionButton mMyFab;

    TextView addNoteActionText, addLinkActionText;
    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMyFab = findViewById(R.id.my_fab);

        mAddNoteFab = findViewById(R.id.add_note_fab);
        mAddLinkFab = findViewById(R.id.add_link_fab);

        addNoteActionText = findViewById(R.id.add_note_action_text);
        addLinkActionText = findViewById(R.id.add_link_action_text);


        mAddNoteFab.setVisibility(View.GONE);
        mAddLinkFab.setVisibility(View.GONE);
        addNoteActionText.setVisibility(View.GONE);
        addLinkActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        mMyFab.shrink();


        mMyFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            mAddNoteFab.show();
                            mAddLinkFab.show();
                            addNoteActionText.setVisibility(View.VISIBLE);
                            addLinkActionText.setVisibility(View.VISIBLE);

                            mMyFab.extend();

                            isAllFabsVisible = true;
                        } else {
                            mAddNoteFab.hide();
                            mAddLinkFab.hide();
                            addNoteActionText.setVisibility(View.GONE);
                            addLinkActionText.setVisibility(View.GONE);

                            mMyFab.shrink();

                            isAllFabsVisible = false;
                        }
                    }
                });


        mAddNoteFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AddNote.class);
                        startActivity(intent);
                    }
                });

        mAddLinkFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT).show();
                    }
                });


        ArrayList<String> myData = new ArrayList<String>();

        SQLiteDatabase myDatabase = openOrCreateDatabase("data", MODE_PRIVATE, null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS notes (Id INTEGER PRIMARY KEY, notesTitle VARCHAR, notesData VARCHAR)");

        //myDatabase.execSQL("INSERT INTO notes (notesTitle, notesData) VALUES ('Title 1', 'Data 1')");
        //myDatabase.execSQL("DELETE from notes");

        //Rearrange the Ids
        Cursor c1 = myDatabase.rawQuery("SELECT * FROM notes", null);
        int id = 1;
        if (c1.moveToFirst()) {
            do {
                myDatabase.execSQL("UPDATE notes SET Id = " + id + " WHERE Id = " + c1.getInt(0));
                id++;
            } while (c1.moveToNext());
        }
        c1.close();

        Cursor c = myDatabase.rawQuery("SELECT * FROM notes", null);

        int idIndex = c.getColumnIndex("Id");
        int notesTitleIndex = c.getColumnIndex("notesTitle");
        int notesDataIndex = c.getColumnIndex("notesData");

        c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                Log.i("Id", Integer.toString(c.getInt(idIndex)));
                Log.i("Title", c.getString(notesTitleIndex));
                Log.i("Data", c.getString(notesDataIndex));

                myData.add(c.getString(notesTitleIndex));
            } while (c.moveToNext());
            // moving our cursor to next.

        }
        c.close();

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myData);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AddNote.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });
    }

    public void addNewNote(View view) {
        Intent intent = new Intent(this, AddNote.class);
        startActivity(intent);
    }
}