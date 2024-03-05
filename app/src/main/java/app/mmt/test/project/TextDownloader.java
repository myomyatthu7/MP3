package app.mmt.test.project;

//import android.os.AsyncTask;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class TextDownloader extends AsyncTask<String ,Void,String> {
//
//    @Override
//    protected String doInBackground(String... urls) {
//        String urlString = urls[0];
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            BufferedReader bfr = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//            StringBuilder sbd = new StringBuilder();
//            String line;
//            while ((line = bfr.readLine())!= null) {
//                sbd.append(line);
//            }
//            return sbd.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TextDownloader {

    public interface DownloadListener {
        void onDownloadComplete(String result);
        void onError(Exception e);
    }

    public void downloadText(String url, DownloadListener listener) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try {
                String result = downloadUrl(url);
                listener.onDownloadComplete(result);
            } catch (Exception e) {
                listener.onError(e);
            }
        });
        executor.shutdown(); // Shutdown the executor after task completion
    }

    private String downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }
}
