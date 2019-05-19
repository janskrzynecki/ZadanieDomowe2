package com.js.zadaniedomowe2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.js.zadaniedomowe2.tasks.TaskListContent;
import java.util.Random;
//Adding new event
public class AddEvent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_event );
    }

    public void Add(View view) {
        EditText nazwa = findViewById ( R.id.editText );
        EditText data = findViewById ( R.id.editText2 );
        EditText opis = findViewById ( R.id.editText3 );
        if(!nazwa.getText ().toString ().equals ( "" ) && !data.getText ().toString ().equals ( "" ) && !opis.getText ().toString ().equals ( "" ))
        {
            Random random = new Random ( );
            String selectedImage;
            Intent intent = getIntent ();
            selectedImage = intent.getStringExtra ( "photo");
            if (intent.getStringExtra ( "photo" )==null) {
                selectedImage="drawable "+(random.nextInt (3)+1);
            }
            TaskListContent.addItem ( new TaskListContent.Task ( "Task." +TaskListContent.ITEMS.size ()+1, nazwa.getText ().toString (), opis.getText ().toString (),data.getText ().toString (), selectedImage ) );
            Intent main_intent=new Intent ( );
            setResult ( RESULT_OK, main_intent );
            finish ();
        }
    }
}
