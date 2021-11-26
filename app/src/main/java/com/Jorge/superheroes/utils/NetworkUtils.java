package com.Jorge.superheroes.utils;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String GITHUB_BASE_URL = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/all.json";
    private static int id;
    static String GITHUB_HEROE_URL = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/id/"+ id +".json";

    public static URL urlHeroe(int idActual){
        id = idActual;
        Uri builtUri = Uri.parse(GITHUB_HEROE_URL).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL urlAll(){
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = urlConnection.getInputStream();

        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");
        try{
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }else{
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
