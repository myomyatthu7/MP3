package android.mmt.lesson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvResult, tvFilePath;
    Button btnLoadData;
    String testPackageName = "app.mmt.test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        tvFilePath = findViewById(R.id.tvFilePath);
        btnLoadData = findViewById(R.id.btnLoadData);
        btnLoadData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLoadData) {
            String filePath = getFilePath();
            FileInputStream fips = null;
            Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
            try {
                StringBuilder sb = new StringBuilder();
                fips = new FileInputStream(filePath);
                int line = -1;
                while ((line = fips.read()) != -1) {
                    sb.append((char) line);
                }
                tvResult.setText(sb.toString());
            } catch (IOException e) {
                tvResult.setText(e.toString());
            } finally {
                if (fips != null) {
                    try {
                        fips.close();
                    } catch (IOException e) {
                        tvResult.setText(e.toString());
                    }
                }
            }
        }
    }

    private String getFilePath() {
        PackageManager pm = getPackageManager();
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(testPackageName, PackageManager.GET_META_DATA);
            return appInfo.dataDir + "files/mmt.txt";
        } catch (PackageManager.NameNotFoundException e) {
            tvFilePath.setText(e.toString());
        }
        return null;
    }
}