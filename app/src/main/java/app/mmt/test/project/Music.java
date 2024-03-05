package app.mmt.test.project;

//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.widget.ImageButton;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.IOException;
//import java.util.Locale;
//
//import app.mmt.test.R;
//
//public class Music extends AppCompatActivity implements View.OnClickListener {
//    ImageButton btnPlayPause;
//    TextView tvCurrentTime, tvTotalDuration;
//    SeekBar seekBar;
//    Handler musicHandler;
//    MediaPlayer mp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mp3);
//
//        // Initialize views
//        btnPlayPause = findViewById(R.id.btnPlayPause);
//        tvCurrentTime = findViewById(R.id.tvCurrentTime);
//        tvTotalDuration = findViewById(R.id.tvTotalDuration);
//        seekBar = findViewById(R.id.seekBar);
//        btnPlayPause.setOnClickListener(this);
//
//        // Initialize MediaPlayer and Handler
//        mp = new MediaPlayer();
//        musicHandler = new Handler();
//
//        // Set maximum value for SeekBar
//        seekBar.setMax(1000);
//
//        // Prepare the music
//        prepareMusic();
//
//        // Pre download the music
//        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//            @Override
//            public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                // Calculate buffer progress within the range of 0 to 1000
//                int bufferProgress = (percent * seekBar.getMax()) / 100;
//                seekBar.setSecondaryProgress(bufferProgress);
//            }
//        });
//
//        // Set listener for SeekBar changes
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (fromUser) {
//                    int playPosition = (mp.getDuration() * progress) / seekBar.getMax();
//                    mp.seekTo(playPosition);
//                    tvCurrentTime.setText(millisecondToTimer(mp.getCurrentPosition()));
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // Not needed for this implementation
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // Not needed for this implementation
//            }
//        });
//    }
//
//    // Prepare the music to be played
//    private void prepareMusic() {
//        try {
//            mp.setDataSource("https://drive.google.com/uc?export=download&id=1pFWAqE-3Jy5qWwb2zYIK00vOxA4jFUjH");
//            mp.prepare();
//            tvTotalDuration.setText(millisecondToTimer(mp.getDuration()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Runnable to update SeekBar and current time TextView
//    Runnable updater = new Runnable() {
//        @Override
//        public void run() {
//            updateSeekbar();
//            long currentSeekbar = mp.getCurrentPosition();
//            tvCurrentTime.setText(millisecondToTimer(currentSeekbar));
//        }
//    };
//
//    // Update SeekBar progress
//    private void updateSeekbar() {
//        if (mp.isPlaying()) {
//            seekBar.setProgress((int) ((float) mp.getCurrentPosition() / mp.getDuration() * 1000));
//            musicHandler.postDelayed(updater, 100);
//        }
//    }
//
//    // Convert milliseconds to timer format (hh:mm:ss)
//    private String millisecondToTimer(long millisecond) {
//        long hours = millisecond / (1000 * 60 * 60);
//        long minutes = (millisecond % (1000 * 60 * 60)) / (1000 * 60);
//        long seconds = (millisecond % (1000 * 60)) / 1000;
//        return String.format(Locale.ROOT, "%01d:%02d:%02d", hours, minutes, seconds);
//    }
//
//    // Handle click events
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btnPlayPause) {
//            if (mp.isPlaying()) {
//                btnPlayPause.setImageResource(R.drawable.play);
//                musicHandler.removeCallbacksAndMessages(updater);
//                mp.pause();
//            } else {
//                mp.start();
//                btnPlayPause.setImageResource(R.drawable.pause);
//                updateSeekbar();
//            }
//        }
//    }
//
//    // Release resources when activity is destroyed
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mp != null) {
//            mp.release();
//            mp = null;
//        }
//    }
//}

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import app.mmt.test.R;

public class Music extends AppCompatActivity implements View.OnClickListener {
    private String fileID;
    private ImageButton btnPlayPause;
    private TextView tvCurrentTime, tvTotalDuration;
    private SeekBar seekBar;
    private Handler musicHandler;
    private MediaPlayer mp;

    private ProgressBar progressBarMusic;
    private boolean isFileDownloaded;
    private File musicFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mp3);

        // Initialize views
        btnPlayPause = findViewById(R.id.btnPlayPause);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalDuration = findViewById(R.id.tvTotalDuration);
        seekBar = findViewById(R.id.seekBar);
        progressBarMusic = findViewById(R.id.progressBarMusic);

        btnPlayPause.setOnClickListener(this);

        // Initialize MediaPlayer and Handler
        mp = new MediaPlayer();
        musicHandler = new Handler();
        fileID = "1pFWAqE-3Jy5qWwb2zYIK00vOxA4jFUjH";
        // Set maximum value for SeekBar
        seekBar.setMax(1000);

        // Find musicFile dir
        musicFile = new File(getFilesDir(), fileID);
        // Check if the music file is already downloaded
        if (musicFile.exists()) {
            Log.d("Google","musicFile exit onCreate");
            isFileDownloaded = true;
            prepareMusic();
        }

        // Set listener for SeekBar changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // Calculate the play position in milliseconds
                    long playPosition = (long) mp.getDuration() * progress / 1000;
                    mp.seekTo((int) playPosition);
                    tvCurrentTime.setText(millisecondToTimer(mp.getCurrentPosition()));
                    Log.d("SeekBar", "User progress: " + progress);
                    Log.d("SeekBar", "Calculated play position: " + playPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set up MediaPlayer buffering update listener
        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });

        // Reset playback controls and prepare music for replay
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("Google","music complete");
                prepareMusic();
            }
        });
        Log.d("Google","onCreate");
    }

    // Prepare the music to be played
    private void prepareMusic() {
        if (isFileDownloaded) {
            Log.d("Google","file exit : prepare");
            // If the music file exists, play it
            playSavedMusic(musicFile);
        } else {
            Log.d("Google","file not exit : prepare");
            // If the music file doesn't exist, download it
            downloadMusic();
        }
    }

    private void downloadMusic() {
        Log.d("Google","call DownloadMusic");
        // Construct the direct download URL
        String directDownloadURL = "https://drive.google.com/uc?export=download&id=" + fileID;
        CheckInternet checkInternet = new CheckInternet(Music.this);
        if (checkInternet.isNetworkAvailable()) {
            progressBarMusic.setVisibility(View.VISIBLE);
            // Start downloading the file
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Download the music file from the URL
                    File musicFile = new File(Music.this.getFilesDir(), fileID);
                    new MusicDownloader().musicDownloader(Music.this, directDownloadURL, fileID, new MusicDownloader.DownloadListener() {
                        @Override
                        public void downloadComplete() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("Google","downloadComplete");
                                    progressBarMusic.setVisibility(View.GONE);
                                    isFileDownloaded = true;
                                    playSavedMusic(musicFile);
                                }
                            });
                        }
                        @Override
                        public void downloadFailed() {
                            Toast.makeText(Music.this, "Download failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        } else {
            Toast.makeText(Music.this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    // Play the saved music file
    private void playSavedMusic(File musicFile) {
        Log.d("Google","call playSavedMusic");
        try {
            mp.reset();
            mp.setDataSource(musicFile.getAbsolutePath());
            mp.prepare();
            btnPlayPause.setImageResource(R.drawable.play);
            updateSeekbar();
            tvTotalDuration.setText(millisecondToTimer(mp.getDuration()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Runnable to update SeekBar and current time TextView
    Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            long currentSeekbar = mp.getCurrentPosition();
            tvCurrentTime.setText(millisecondToTimer(currentSeekbar));
        }
    };

    // Update SeekBar progress
    private void updateSeekbar() {
        if (mp.isPlaying()) {
            seekBar.setProgress((int) ((float) mp.getCurrentPosition() / mp.getDuration() * 1000));
            musicHandler.postDelayed(updater, 100);
        }
    }

    // Convert milliseconds to timer format (hh:mm:ss)
    private String millisecondToTimer(long millisecond) {
        long hours = millisecond / (1000 * 60 * 60);
        long minutes = (millisecond % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (millisecond % (1000 * 60)) / 1000;
        return String.format(Locale.ROOT, "%01d:%02d:%02d", hours, minutes, seconds);
    }

    // Handle click events
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlayPause) {
            if (mp.isPlaying()) {
                Log.d("Google","playing");
                mp.pause();
                btnPlayPause.setImageResource(R.drawable.play);
                musicHandler.removeCallbacksAndMessages(updater);
            } else if (!mp.isPlaying() && isFileDownloaded){
                Log.d("Google","isnotPlaying, file exit");
                mp.start();
                btnPlayPause.setImageResource(R.drawable.pause);
                updateSeekbar();
            } else {
                Log.d("Google","isnotPlaying, file not exit");
                prepareMusic();
            }
        }
    }

    // Release resources when activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}
