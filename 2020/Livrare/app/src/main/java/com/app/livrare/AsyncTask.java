package com.app.livrare;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AsyncTask extends android.os.AsyncTask<String, Void,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            if (urlConnection instanceof HttpURLConnection) {
                httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.connect();
                int resultCode = httpURLConnection.getResponseCode();
                if (resultCode == httpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name()));
                    StringBuilder stringBuilder = new StringBuilder();
                    try (Reader reader = new BufferedReader(inputStreamReader)) {
                        int character = 0;
                        while ((character = reader.read()) != -1) {
                            stringBuilder.append((char) character);
                        }
                    }
                    result = stringBuilder.toString();
                } else {
                    throw new Exception("Invalid HTTP connection");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return result;
    }
}
