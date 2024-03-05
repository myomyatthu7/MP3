package app.mmt.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyText extends AppCompatActivity {

    private static ProgressBar progressBar;
    private static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mytext);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);

        new TextDownloader().execute("https://docs.google.com/document/d/1NTb1AvaDLwicuFhvnHyjB8OzmI73AYdGprZ3k8hyEtE/export?format=txt");
    }

    private static class TextDownloader extends AsyncTask<String, Void, String> {
        ArrayList<String > titleArrayList = new ArrayList<>();
        ArrayList<String > descArrayList = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            // Show the progress bar before starting the task
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            String urlString = urls[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Hide the progress bar and display the text
            progressBar.setVisibility(ProgressBar.GONE);
            if (result != null) {
                textView.setText(result);
                String[] ary = result.split("\\|");
                for (int i =0;i<ary.length;i++) {
                    if (i%2!=0) {
                        int j = i-1;
                        descArrayList.add(ary[i]);
                        titleArrayList.add(ary[j]);
                    }
                }
            }
            Log.d("GoogleData",titleArrayList.toString());
            Log.d("GoogleData",descArrayList.toString());
        }
    }
}
