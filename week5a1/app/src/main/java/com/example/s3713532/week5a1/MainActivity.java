package com.example.s3713532.week5a1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    protected String json;
    protected ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        // call the private class
        new GetStudent().execute();
    }

    private class GetStudent extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            json = HttpHandler.get("http://bestlab.us:8080/students");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(MainActivity.this, json, Toast.LENGTH_SHORT).show();

            Gson gson = new Gson();
            Student[] students = gson.fromJson(json, Student[].class);

            ArrayAdapter<Student> aa = new ArrayAdapter<Student>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, students);
            list.setAdapter(aa);
        }
    }
}
