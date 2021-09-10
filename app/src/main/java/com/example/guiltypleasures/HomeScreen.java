package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private ActionBar toolbar;
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=8ef07998664a58a01082bc2ab507fcb8";

    List<UpcomingMovieClass> upcomingMovies;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        upcomingMovies = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);

        toolbar = getSupportActionBar();

        //GetData getData = new GetData();
        //getData.execute();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.homepage:
                            startActivity(new Intent(HomeScreen.this, HomeScreen.class));
                            HomeScreen.this.finish();
                            break;
                        case R.id.database:
                            startActivity(new Intent(HomeScreen.this, Database.class));
                            HomeScreen.this.finish();
                            break;
                        case R.id.list:
                            startActivity(new Intent(HomeScreen.this, List.class));
                            HomeScreen.this.finish();
                            break;
                        case R.id.profile:
                            startActivity(new Intent(HomeScreen.this, UserProfile.class));
                            HomeScreen.this.finish();
                            break;
                    }

                    return true;
                }
            };
    public class GetData extends AsyncTask<String, String , String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader read = new InputStreamReader(is);

                    int data = read.read();

                    while (data != -1) {
                        current += (char) data;
                        data = read.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    UpcomingMovieClass up = new UpcomingMovieClass();

                    up.setId(jsonObject1.getString("vote_average"));
                    up.setTitle(jsonObject1.getString("title"));
                    up.setImg(jsonObject1.getString("poster_path"));

                    upcomingMovies.add(up);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(upcomingMovies);

        }
    }
    private void PutDataIntoRecyclerView(List<UpcomingMovieClass> upcomingMovies) {
        MovieAdapter adapter = new MovieAdapter(this, upcomingMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}