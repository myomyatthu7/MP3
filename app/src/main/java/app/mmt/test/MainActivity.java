package app.mmt.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Animation animation;
    //LottieAnimationView lottieAnimationView;
    Button btnAnim,btnSave;
    TextView tvFilePath;
    EditText etInput;
    //ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iv = findViewById(R.id.iv);
        btnSave = findViewById(R.id.btnSave);
        etInput = findViewById(R.id.etInput);
        tvFilePath = findViewById(R.id.tvFilePath);
        btnAnim = findViewById(R.id.btnAnim);
        //animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
        //iv.setAnimation(animation);
        btnAnim.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            String input = etInput.getText().toString();
            File f = getFilesDir();
            File file = new File(f,"mmt.txt");
            FileOutputStream fops = null;
            try {
                fops = new FileOutputStream(file);
                fops.write(input.getBytes());
                tvFilePath.setText(input+" is saved at "+file.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (fops!= null) {
                    try {
                        fops.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

//            String input = etInput.getText().toString();
//            FileOutputStream fops= null;
//            try {
//                File f = getFilesDir();
//                fops = openFileOutput("mmt.txt",Context.MODE_PRIVATE);
//                fops.write(input.getBytes());
//                tvFilePath.setText(input +" at saved in"+f.getAbsolutePath()+"/mmt.txt");
//            } catch (IOException e) {
//                tvFilePath.setText(e.toString());
//            }finally {
//                if (fops !=null) {
//                    try {
//                        fops.close();
//                    } catch (IOException e) {
//                        tvFilePath.setText(e.toString());
//                    }
//                }
//            }
//
            } else if (v.getId() == R.id.btnAnim) {
            File f = getFilesDir();
            f = new File(f,"mmt.txt");
            StringBuffer sbf = new StringBuffer();
            FileInputStream fips = null;
            try {
                fips =  new FileInputStream(f);
                int line = -1;
                while ((line = fips.read()) !=-1) {
                    sbf.append((char) line);
                }
                tvFilePath.setText(sbf.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (fips != null) {
                    try {
                        fips.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
     }
}
