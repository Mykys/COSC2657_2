package com.example.s3713532.week5a1;

import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by s3713532 on 3/26/18.
 */

public class HttpHandler {

    public static void main(String[] args) {
        System.out.println(HttpHandler.get("http://bestlab.us:8080/students"));

//        String json = HttpHandler.get("http://bestlab.us:8080/students");
//
//        Gson gson = new Gson();
//        Student[] students = gson.fromJson(json, Student[].class);
//
//        for (Student student : students) {
//            System.out.println(student.getName());
//        }
//
//        // get all places
//
//        String jsonPlace = HttpHandler.get("http://bestlab.us:8080/places");
//
//        Places[] places = gson.fromJson(jsonPlace, Places[].class);
//
//        for (Places place : places) {
//            System.out.println(place.getName());
//        }
    }

    public static String get(String urlStr) {

        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // BufferedReader is responsible for reading all the data
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while (true) {
                line = bufferedReader.readLine();
                if (line == null) break;
                stringBuilder.append(line);
            }
            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
