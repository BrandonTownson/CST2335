package com.example.brandon.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FinalAssignment extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "FinalAssignment";

    Button sendMsg;
    Button openB;
    Button closeB;
    Button halfB;
    ProgressBar pBar;
    EditText textField;
    ListView listShow;
    ArrayList<String> arrayString = new ArrayList<>();

    ChatDataBaseHelper helper;
    SQLiteDatabase db;
    ContentValues vc = new ContentValues();
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG)
                        .setAction("Anti_action", null).show();
            }
        });


        helper = new ChatDataBaseHelper(this);
        db=helper.getWritableDatabase();

        c= db.query(false,helper.name,new String[]{helper.KEY_ID,helper.KEY_MESSAGE},"ID NOT NULL",null,null,null,null,null);
        c.moveToFirst();

        sendMsg = (Button) findViewById(R.id.send_msg);
        textField = (EditText) findViewById(R.id.chatWindow);
        listShow = (ListView) findViewById(R.id.listView);
        final FinalAssignment.ChatAdapter messageAdapter = new FinalAssignment.ChatAdapter(this);
        listShow.setAdapter(messageAdapter);

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayString.add(textField.getText().toString());
                vc.put(helper.KEY_MESSAGE,textField.getText().toString());
                db.insert(helper.name,"NULL REPLACEMENT VALUE",vc);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()
                textField.setText("");
            }
        });

        openB = (Button) findViewById(R.id.Open);
        openB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pBar =(ProgressBar) findViewById(R.id.progressBar2);

                pBar.setVisibility(View.VISIBLE);

                while (pBar.getProgress()!=100){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e){
                        Log.e("Here is the stack trace",e.getMessage());
                    }
                   pBar.incrementProgressBy(10);
                }

                if (pBar.getProgress()==100){
                    Toast.makeText(getApplication(),"The blinds are now open",Toast.LENGTH_SHORT).show();
                    pBar.setProgress(0);
                    pBar.setVisibility(View.INVISIBLE);
                }

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        db.close();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop");

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume");

    }

    class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return arrayString.size();
        }

        @Override
        public String getItem(int position) {
            return arrayString.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = FinalAssignment.this.getLayoutInflater();

            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }

    }

    public class progressBar extends AsyncTask<String,Integer,String>{
        protected String doInBackground(String ...args){

            return null;
        }

        }

}
