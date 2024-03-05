package app.mmt.test.project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import app.mmt.test.ToastMsg;

public class MusicDownloader {
    interface DownloadListener {
        void downloadComplete();
        void downloadFailed();
    }
    public void musicDownloader(Context context, String directDownloadURL, String fileID,DownloadListener downloadListener) {


        try {
            URL url = new URL(directDownloadURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Check if the response code indicates success (HTTP 200)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the InputStream from the connection
                InputStream inputStream = connection.getInputStream();
                // Create a FileOutputStream to save the downloaded file
                File musicFile = new File(context.getFilesDir(), fileID);
                FileOutputStream outputStream = new FileOutputStream(musicFile);

                // Read the data from the InputStream and write it to the FileOutputStream
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // Close the streams
                outputStream.close();
                inputStream.close();
                downloadListener.downloadComplete();
            } else {
                // Handle unsuccessful response
                // For example, show an error message or retry the download
                Log.e("Download", "HTTP error code: " + connection.getResponseCode());
                downloadListener.downloadFailed();
            }
            // Disconnect the connection
            connection.disconnect();
        } catch (IOException e) {
            // Handle IOException
            Log.e("Download", e.toString());
        }
    }

}
