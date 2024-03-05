package app.mmt.test.project;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;

import app.mmt.test.ToastMsg;

public class MyBuffer {

    public void writeToInternalStorage(Context context, String fileName, String data) {
            try {
                File f = context.getFilesDir();
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                writer.write(data);
                writer.close();
                fos.close();
                //System.out.println("Data written to file: " + fileName);
                ToastMsg.toastMsgShort(context,f.getAbsolutePath()+"/"+fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public boolean doesFileExist(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file.exists();
    }
    public String readFromInternalStorage(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
