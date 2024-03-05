package app.mmt.test.project;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MyBufferHelper {
    private Context context;
    private String fileName;
    private String data;
    public MyBufferHelper(Context context,String fileName,String data) {
        context = this.context;
        fileName = this.fileName;
        data = this.data;
    }
    public void createFile() {
        File f = context.getFilesDir();
        try {
            FileOutputStream fops = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fops));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
