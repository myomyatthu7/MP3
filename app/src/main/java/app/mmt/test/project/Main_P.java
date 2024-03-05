package app.mmt.test.project;

//import android.os.AsyncTask;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.concurrent.ExecutionException;
//
//import app.mmt.test.R;
//
//public class Main_P extends AppCompatActivity {
//    String [] array;
//    RecyclerView recMain_P;
//    MyData myData;
//    MyAdapter myAdapter;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_p);
//        recMain_P = findViewById(R.id.recMain_P);
//        AsyncTask<String, Void, String> result = new TextDownloader().execute("https://docs.google.com/document/d/1NTb1AvaDLwicuFhvnHyjB8OzmI73AYdGprZ3k8hyEtE/export?format=txt");
//        String ss = null;
//        try {
//            ss = result.get();
//        } catch (ExecutionException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        ArrayList<MyData> arrayList = new ArrayList<>();
//        array = ss.split("/");
//        for (int i =0;i<array.length;i++) {
//            if (i%2!=0) {
//                int j = i-1;
//                myData = new MyData(array[j],array[i]);
//                arrayList.add(myData);
//            }
//        }
//        recMain_P.setLayoutManager(new LinearLayoutManager(Main_P.this));
//        myAdapter = new MyAdapter(Main_P.this,arrayList);
//        recMain_P.setAdapter(myAdapter);
//    }
//}

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import app.mmt.test.R;
import app.mmt.test.ToastMsg;

public class Main_P extends AppCompatActivity implements TextDownloader.DownloadListener {
    String[] array;
    RecyclerView recMain_P;
    MyData myData;
    MyAdapter myAdapter;
    ProgressBar progressBar;
    MyBuffer myBuffer;
    String fileName = "dhamma.txt";
    String writeResult = "";
    String readResult = "";
    Button btnUpdateContent;
    CheckInternet checkInternet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_p);

        recMain_P = findViewById(R.id.recMain_P);
        progressBar = findViewById(R.id.progressBarDhamma);
        btnUpdateContent = findViewById(R.id.btnUpdateContent);
        myBuffer = new MyBuffer();
        checkInternet = new CheckInternet(Main_P.this);
        recMain_P.setLayoutManager(new LinearLayoutManager(Main_P.this));
        btnUpdateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE); // Show progress bar initially
                if (checkInternet.isNetworkAvailable()) {
                    TextDownloader textDownloader = new TextDownloader();
                    // https://docs.google.com/document/d/1NTb1AvaDLwicuFhvnHyjB8OzmI73AYdGprZ3k8hyEtE/edit?usp=sharing
                    textDownloader.downloadText("https://docs.google.com/document/d/1NTb1AvaDLwicuFhvnHyjB8OzmI73AYdGprZ3k8hyEtE/export?format=txt", Main_P.this);
                } else {
                    Toast.makeText(Main_P.this, "No Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (myBuffer.doesFileExist(Main_P.this,fileName)) {
            readResult = myBuffer.readFromInternalStorage(Main_P.this,fileName);
            ArrayList<MyData> arrayList = new ArrayList<>();
            array = readResult.split("/");
            Log.d("Google",writeResult);
            for (int i = 0; i < array.length; i++) {
                if (i % 2 != 0) {
                    int j = i - 1;
                    myData = new MyData(array[j], array[i]);
                    arrayList.add(myData);
                }
            }
            myAdapter = new MyAdapter(Main_P.this, arrayList);
            recMain_P.setAdapter(myAdapter);
        } else {
            if (checkInternet.isNetworkAvailable()) {
                progressBar.setVisibility(View.VISIBLE); // Show progress bar initially
                TextDownloader textDownloader = new TextDownloader();
                // https://docs.google.com/document/d/1NTb1AvaDLwicuFhvnHyjB8OzmI73AYdGprZ3k8hyEtE/edit?usp=sharing
                textDownloader.downloadText("https://docs.google.com/document/d/1NTb1AvaDLwicuFhvnHyjB8OzmI73AYdGprZ3k8hyEtE/export?format=txt", Main_P.this);
            } else {
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onDownloadComplete(String result) {
//        // This method will be called when the download completes successfully
        writeResult = result;
        ArrayList<MyData> arrayList = new ArrayList<>();
        array = result.split("/");
        Log.d("Google",writeResult);
        for (int i = 0; i < array.length; i++) {
            if (i % 2 != 0) {
                int j = i - 1;
                myData = new MyData(array[j], array[i]);
                arrayList.add(myData);
            }
        }
        // Update UI on the main thread
        new Handler(Looper.getMainLooper()).post(() -> {
            myAdapter = new MyAdapter(Main_P.this, arrayList);
            recMain_P.setAdapter(myAdapter);
            progressBar.setVisibility(View.GONE); // Hide progress bar when download is finished
            myBuffer.writeToInternalStorage(Main_P.this,fileName,result);
        });
    }

    @Override
    public void onError(Exception e) {
        // This method will be called if an error occurs during download
        Log.e("Main_P", "Error occurred during download: " + e.getMessage());

        // Update UI on the main thread
        new Handler(Looper.getMainLooper()).post(() -> {
            progressBar.setVisibility(View.GONE); // Hide progress bar on error
        });
    }
}
