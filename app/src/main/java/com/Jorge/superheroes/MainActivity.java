package com.Jorge.superheroes;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.Jorge.superheroes.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView urlDisplay;
    TextView searchResults;
    TextView mensajeError;
    ProgressBar circuloCarga;

    public class  GithubQueryTask extends AsyncTask<URL, Void,String> {
        protected void onPreExecute(){circuloCarga.setVisibility(View.VISIBLE);}
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String resultSearch = null;
            try {
                resultSearch = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultSearch;
        }

        private void showResults(){
            mensajeError.setVisibility(View.INVISIBLE);
            searchResults.setVisibility(View.VISIBLE);
        }
        private void showError(){
            mensajeError.setVisibility(View.VISIBLE);
            searchResults.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(String s){
            circuloCarga.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")){
                showResults();
                searchResults.setText(s);
            } else{
                showError();
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.i("MainActivity ItemID", String.valueOf(itemId));
        if (itemId == R.id.search){
            Log.i("MainActivity", "El usuario ha pulsado search");
            Context context = MainActivity.this;
            Toast.makeText(context, R.string.search_pressed,Toast.LENGTH_LONG).show();

            URL githubUrl = NetworkUtils.urlAll();
            urlDisplay.setText(githubUrl.toString());

            new GithubQueryTask().execute(githubUrl);
        }else if(itemId == R.id.clear){
            urlDisplay.setText("");
            searchResults.setText("");
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlDisplay= (TextView) findViewById((R.id.url_display));
        searchResults= (TextView) findViewById((R.id.github_search_results));
        mensajeError = (TextView) findViewById((R.id.error_message));
        circuloCarga = (ProgressBar) findViewById((R.id.request_progress));
    }
}