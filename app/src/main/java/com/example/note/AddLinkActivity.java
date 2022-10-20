package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddLinkActivity extends AppCompatActivity {

    public boolean updateNote = false;
    public SQLiteDatabase myDatabase;
    public int idIndex;
    public int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Link");


        myDatabase = openOrCreateDatabase("data", MODE_PRIVATE, null);

        Intent gotIntent = getIntent();
        int noteId = gotIntent.getIntExtra("noteId", -1) + 1;



        if (noteId != 0) {
            updateNote = true;

            Button updateButton = findViewById(R.id.button);
            updateButton.setText("Update");

            Cursor c = myDatabase.rawQuery("SELECT * from notes where Id=" + noteId, null);
            idIndex = c.getColumnIndex("Id");
            int notesTitleIndex = c.getColumnIndex("notesTitle");
            int notesDataIndex = c.getColumnIndex("notesData");

            c.moveToFirst();
            if (c.moveToFirst()) {
                do {
                    //Log.i("Id", Integer.toString(c.getInt(idIndex)));
                    //Log.i("Title", c.getString(notesTitleIndex));
                    //Log.i("Data", c.getString(notesDataIndex));

                    Id = c.getInt(idIndex);

                    TextView noteTitle = findViewById(R.id.noteTitle);
                    noteTitle.setText(c.getString(notesTitleIndex));

                    TextView noteData = findViewById(R.id.noteData);
                    noteData.setText(c.getString(notesDataIndex));

                } while (c.moveToNext());
            }
            c.close();

        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveNote(View view) {

        TextView noteTitle = findViewById(R.id.noteTitle);
        String title = noteTitle.getText().toString();

        TextView noteData = findViewById(R.id.noteData);
        String data = noteData.getText().toString();

        if (updateNote) {

            //Update the Data
            ContentValues cv = new ContentValues();
            cv.put("notesTitle", title);
            cv.put("notesData", data);
            //myDatabase.update("notes", cv, "Id=" + (Id), null);

            //Toast.makeText(this, Integer.toString(Id), Toast.LENGTH_SHORT).show();

            //Update the data without cv
            myDatabase.execSQL("UPDATE notes SET notesTitle = '"+title+"', notesData = '"+data+"' WHERE Id = "+(Id));

            Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
        } else {
            myDatabase.execSQL("INSERT INTO notes (notesTitle, notesData, notesType) VALUES ('" + title + "' , '" + data + "', 'link')");
            //Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}