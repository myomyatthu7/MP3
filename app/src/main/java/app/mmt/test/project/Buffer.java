package app.mmt.test.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import app.mmt.test.R;
import app.mmt.test.ToastMsg;

public class Buffer extends AppCompatActivity implements View.OnClickListener {
    TextView tvShowPath,tvShowText;
    Button btnCreate,btnReadFile;
    String str = "Hello Kotlin\nHello World";
    File file;
    String fileName = "test.txt";
    MyBuffer myBuffer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buffer);
        tvShowPath = findViewById(R.id.tvShowPath);
        tvShowText = findViewById(R.id.tvShowText);
        btnCreate = findViewById(R.id.btnCreateFile);
        btnReadFile = findViewById(R.id.btnReadFile);

        btnCreate.setOnClickListener(this);
        btnReadFile.setOnClickListener(this);
        myBuffer = new MyBuffer();
    }

    @Override
    public void onClick(View v) {
        String internetStr = "Hello Java\nHello World\nInternet";
        String noInternetStr = "Hello Kotlin\nHello World\nNo Internet";
        if (v.getId() == R.id.btnCreateFile) {
            boolean internet = false;
            if (internet) {
                myBuffer.writeToInternalStorage(Buffer.this,fileName,internetStr);
            } else {
                if (myBuffer.doesFileExist(Buffer.this,fileName)) {
                    myBuffer.writeToInternalStorage(Buffer.this,fileName,noInternetStr);
                } else {
                    ToastMsg.toastMsgShort(Buffer.this,"No Internet");
                }
            }
        } else if (v.getId() == R.id.btnReadFile) {
            String text = myBuffer.readFromInternalStorage(Buffer.this,fileName);
            tvShowText.setText(text);
        }
    }

}
