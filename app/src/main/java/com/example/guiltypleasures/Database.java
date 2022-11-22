package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Database extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //JSON Link
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=8ef07998664a58a01082bc2ab507fcb8";

    //variables
    private ActionBar toolbar;
    private String selection;

    //variables
    java.util.List<UpcomingMovieClass> movieFilter;

    //user interface variables
    RecyclerView recyclerView;

    //creation of database page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        //set variables
        toolbar = getSupportActionBar();

        movieFilter = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView2);


        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"upcoming", "popular", "now_playing", "top_rated", "latest"};

        //store information in spinner(DropDown List)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        //create nav bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.database);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);
    }

    //GetData Class for Database SCREEN
    public class GetData extends AsyncTask<String, String , String> {
        @Override
        protected String doInBackground(String... strings) {
            //variable
            String current = "";

            try {
                //URL & HTTP variables
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    //create URL with JSON connection and initialize connection
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    //Gather Input Data from URL connection
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader read = new InputStreamReader(is);

                    //Read Data Gathered until end
                    int data = read.read();
                    while (data != -1) {
                        current += (char) data;
                        data = read.read();
                    }

                    //return Results
                    return current;
                }
                //catch errors of 2nd Try Statement
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                //Do this regardless of results of 2nd Try Statement
                finally {
                    //make sure connection is disabled
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
            //catch errors of 1st Try Statement
            catch (Exception e) {
                e.printStackTrace();
            }

            //return Results
            return current;
        }



        @Override
        protected void onPostExecute(String s) {
            try {
                //create a JSON object of String Parameter
                JSONObject jsonObject = new JSONObject(s);

                //create Json Array based on name on list's name
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                //for loop to add all objects to Movie Array List
                for(int i = 0; i < jsonArray.length(); i++) {
                    //Create movie Json Object
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    UpcomingMovieClass up = new UpcomingMovieClass();

                    //set Properties
                    up.setId(jsonObject1.getString("title"));
                    up.setTitle(jsonObject1.getString("vote_average"));
                    up.setImg(jsonObject1.getString("poster_path"));

                    //add to Array List
                    movieFilter.add(up);
                }

            }
            //catch errors of Try Statement
            catch (JSONException e) {
                e.printStackTrace();
            }

            //Push data into View
            PutDataIntoRecyclerView(movieFilter);
        }
    }

    private void PutDataIntoRecyclerView(java.util.List<UpcomingMovieClass> movieFilter) {
        //create object of adapter and set it in the recycler view
        MovieAdapter adapter = new MovieAdapter(this, movieFilter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    //creation of nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener NavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.homepage:
                            startActivity(new Intent(Database.this, HomeScreen.class));
                            Database.this.finish();
                            break;
                        case R.id.database:
                            startActivity(new Intent(Database.this, Database.class));
                            Database.this.finish();
                            break;
                        case R.id.list:
                            startActivity(new Intent(Database.this, List.class));
                            Database.this.finish();
                            break;
                        case R.id.profile:
                            startActivity(new Intent(Database.this, UserProfile.class));
                            Database.this.finish();
                            break;
                    }

                    return true;
                }
            };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selection = parent.getItemAtPosition(position).toString();

        JSON_URL = "https://api.themoviedb.org/3/movie/" + selection + "?api_key=8ef07998664a58a01082bc2ab507fcb8";

        movieFilter.clear();

        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}