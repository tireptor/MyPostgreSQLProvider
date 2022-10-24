package com.example.mypostgresqlprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private Database myDatabase ;
    private String records;
    private TextView myTextRecords;
    private Button myButton;
    public static final String TAG = "Vincent";
   // private Task myTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextRecords = findViewById(R.id.main_textview_records);
        myButton = findViewById(R.id.main_button_select);
        Log.d(TAG, "Initialisation ...");
        myDatabase = new Database();
        Log.d(TAG, "Instance de database créée ...");

        myButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "On va à la pêche !");
                myTextRecords.setText("");
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
                //selectPoisson();
                Log.d(TAG, "Après la pêche !");
            }
        });

    }
    private void selectPoisson(){
        Log.d(TAG, "Début de select");
        ResultSet resultSet = null;
       // myTextRecords.setText("");
        Log.d(TAG, "Avant le try select");
        try {
            resultSet = myDatabase.Select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Parcours des résultats");
        records = "";
        while (true) {
            try {
                if (!resultSet.
                        next()) break;
                records += resultSet.getString(1) + "\n";
            } catch (SQLException e) {
                e.printStackTrace();
                break;
            }

        }
    }
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                selectPoisson();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Records : "+records);
            myTextRecords.setText(records);
        }


        @Override
        protected void onPreExecute() {
           // progressDialog = ProgressDialog.show(MainActivity.this,
            //        "ProgressDialog",
            //        "Wait for "+time.getText().toString()+ " seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {
           // finalResult.setText(text[0]);

        }
    }
}