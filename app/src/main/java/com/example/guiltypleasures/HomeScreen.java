package com.example.guiltypleasures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
import java.util.List;

public class HomeScreen extends AppCompatActivity {
    //JSON Link
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=8ef07998664a58a01082bc2ab507fcb8";
    private String JSON_URL_POPUP = "https://api.themoviedb.org/3/movie/popular?api_key=8ef07998664a58a01082bc2ab507fcb8";

    //variables
    List<UpcomingMovieClass> upcomingMovies;
    List<UpcomingMovieClass> popularMovies;

    //user interface variables
    RecyclerView recyclerView;

    //popup variables
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    RecyclerView recyclerViewPopupAds;
    ImageView popupExit;

    //Shared Preferences
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    //create Home Screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        //set and create variables
        upcomingMovies = new ArrayList<>();
        popularMovies = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        //shared preferences
        mPreferences = getSharedPreferences("com.example.Prefs_Guilty_Pleasures", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        //create object of class and execute methods
        GetData getData = new GetData();
        getData.execute();

        //Call Popup
        boolean PopUpCall = mPreferences.getBoolean("DialogShow", true);

        if(PopUpCall) {
            createNewContactDialog();
            mEditor.putBoolean("DialogShow", false);
            mEditor.apply();

            GetDataPopup popup = new GetDataPopup();
            popup.execute();
        }

        //Create bottom navigation bar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.homepage);
        bottomNav.setOnNavigationItemSelectedListener(NavigationListener);
    }

    //GetData Class for HOME SCREEN
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
                    upcomingMovies.add(up);
                }

            }
            //catch errors of Try Statement
            catch (JSONException e) {
                e.printStackTrace();
            }

            //Push data into View
            PutDataIntoRecyclerView(upcomingMovies);
        }
    }

    private void PutDataIntoRecyclerView(List<UpcomingMovieClass> upcomingMovies) {
        //create object of adapter and set it in the recycler view
        MovieAdapter adapter = new MovieAdapter(this, upcomingMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    //GetData Class for HOME SCREEN
    public class GetDataPopup extends AsyncTask<String, String , String> {
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
                    url = new URL(JSON_URL_POPUP);
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
                    popularMovies.add(up);
                }

            }
            //catch errors of Try Statement
            catch (JSONException e) {
                e.printStackTrace();
            }

            //Push data into View
            PutDataIntoRecyclerViewPopup(popularMovies);
        }
    }

    //create object of adapter and set it in the recycler view
    private void PutDataIntoRecyclerViewPopup(List<UpcomingMovieClass> popularMovies) {
        MovieAdapter adapter = new MovieAdapter(this, popularMovies);
        recyclerViewPopupAds.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPopupAds.setAdapter(adapter);
    }


    //Creation of the navigation bar
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
                            startActivity(new Intent(HomeScreen.this, com.example.guiltypleasures.List.class));
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


    //Create Pop up Window
    public void createNewContactDialog(){
        //Begin Creation
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.activity_top_advertisement, null);

        //Setup User Variables
        popupExit = (ImageView) contactPopupView.findViewById(R.id.ExitButton);
        recyclerViewPopupAds = (RecyclerView) contactPopupView.findViewById(R.id.recyclerView3);

        //Build the Popup
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        //Button to dismiss popup
        popupExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}