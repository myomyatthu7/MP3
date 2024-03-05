package app.mmt.test.project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import app.mmt.test.R;

public class Detail_P extends AppCompatActivity implements TextDownloader.DownloadListener {
    TextView tvTitle_Detail,tvDesc_Detail;
    ProgressBar progressBarDetail;
    StringBuilder stringBuilder;
    Button btnUpdateContentDetail;
    CheckInternet checkInternet;
    MyBuffer myBuffer;
    String descUrl=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_p);
        tvTitle_Detail = findViewById(R.id.tvTitle_Detail);
        tvDesc_Detail = findViewById(R.id.tvDesc_Detail);
        btnUpdateContentDetail = findViewById(R.id.btnUpdateContentDetail);
        progressBarDetail = findViewById(R.id.progressBarDetail);
        Bundle bd = getIntent().getExtras();
        String title_detail = null;
        if (bd != null) {
            title_detail = bd.getString("Title");
        }
        String descAndMp3;

        String mp3Url=null;
        if (bd != null) {
            descAndMp3 = bd.getString("Desc");
            String[] ary = new String[0];
            if (descAndMp3 != null) {
                ary = descAndMp3.split("%");
            }
            descUrl = ary[0];
//            mp3Url = ary[1];
        }
        tvTitle_Detail.setText(title_detail);
        checkInternet = new CheckInternet(Detail_P.this);
        myBuffer = new MyBuffer();
        btnUpdateContentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternet.isNetworkAvailable()) {
                    progressBarDetail.setVisibility(View.VISIBLE);
                    TextDownloader textDownloader = new TextDownloader();
                    textDownloader.downloadText("https://docs.google.com/document/d/"+
                            descUrl +"/export?format=txt", Detail_P.this);
                } else {
                    Toast.makeText(Detail_P.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (myBuffer.doesFileExist(Detail_P.this,descUrl)) {
            String readDesc = myBuffer.readFromInternalStorage(Detail_P.this,descUrl);
            tvDesc_Detail.setText(readDesc);
        } else {
            if (checkInternet.isNetworkAvailable()) {
                progressBarDetail.setVisibility(View.VISIBLE);
                TextDownloader textDownloader = new TextDownloader();
                textDownloader.downloadText("https://docs.google.com/document/d/"+
                        descUrl +"/export?format=txt", Detail_P.this);
            } else {
                Toast.makeText(Detail_P.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onDownloadComplete(String result) {
        runOnUiThread(() -> {
            // Update UI on the main UI thread
            String[] strAry = result.split("/");
            stringBuilder = new StringBuilder();
            for (String str : strAry) {
                stringBuilder.append(str).append("\n");
            }
            //Log.d("Google", stringBuilder.toString());
            progressBarDetail.setVisibility(View.GONE);
            tvDesc_Detail.setText(stringBuilder.toString());
            myBuffer.writeToInternalStorage(Detail_P.this,descUrl,stringBuilder.toString());
        });
    }

    @Override
    public void onError(Exception e) {
        Log.d("Google", e.toString());
        // Handle error here if needed
    }
}

//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import app.mmt.test.R;
//
//public class Detail_P extends AppCompatActivity {
//
//    // use for text on googel drive not google docs
//
//    private TextView textViewContent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.detail_p);
//
//        textViewContent = findViewById(R.id.tvDesc_Detail);
//        TextView tvTitle_Detail = findViewById(R.id.tvTitle_Detail);
//       // https://drive.google.com/file/d/1woHaLJxMoeDL9KImBon7S_XdQuIemO8L/view?usp=sharing
//
//        // Execute AsyncTask to fetch text content from an online source
//        new FetchTextTask().execute("https://drive.google.com/uc?export=download&id=1woHaLJxMoeDL9KImBon7S_XdQuIemO8L");
//    }
//
//    private class FetchTextTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            if (urls.length == 0) return null;
//
//            String urlString = urls[0];
//            try {
//                URL url = new URL(urlString);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                try {
//                    InputStream inputStream = urlConnection.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//                    return stringBuilder.toString();
//                } finally {
//                    urlConnection.disconnect();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                textViewContent.setText(result);
//            } else {
//                Toast.makeText(Detail_P.this, "Failed to fetch text", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}


