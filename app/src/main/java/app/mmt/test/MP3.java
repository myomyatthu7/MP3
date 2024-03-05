package app.mmt.test;

//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.IOException;
//
//public class MP3 extends AppCompatActivity {
//
//    private MediaPlayer mediaPlayer;
//    private Button buttonPlay;
//    private Button buttonPause;
//    private Button buttonStop;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mp3);
//
//        // Initialize MediaPlayer
//        mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer
//                    .setDataSource("https://drive.google.com/uc?export=download&id=1pFWAqE-3Jy5qWwb2zYIK00vOxA4jFUjH");
//
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//
//        // Initialize buttons
//        buttonPlay = findViewById(R.id.buttonPlay);
//        buttonPause = findViewById(R.id.buttonPause);
//        buttonStop = findViewById(R.id.buttonStop);
//
//        // Set click listeners
//        buttonPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mediaPlayer.isPlaying()) {
//                    mediaPlayer.start();
//                }
//            }
//        });
//
//        buttonPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                }
//            }
//        });
//
//        buttonStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                    try {
//                        mediaPlayer.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
//}
//

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Locale;

public class MP3 extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mediaPlayer;
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonStop;
    private SeekBar seekBar;
    private TextView textViewCurrentTime;
    private TextView textViewTotalDuration;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mp3);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this); // Set buffering update listener
        try {
            mediaPlayer.setDataSource("https://drive.google.com/uc?export=download&id=1pFWAqE-3Jy5qWwb2zYIK00vOxA4jFUjH");
            mediaPlayer.prepareAsync(); // Prepare asynchronously
            mediaPlayer.setOnPreparedListener(mp -> {
                // Prepare completed, start playback
                mp.start();
                updateSeekBar(); // Start updating seekbar
                updateTextViewTotalDuration(); // Update total duration
            });
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        // Initialize buttons
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPause = findViewById(R.id.buttonPause);
        buttonStop = findViewById(R.id.buttonStop);

        // Initialize seekbar and text views
        seekBar = findViewById(R.id.seekBar);
        textViewCurrentTime = findViewById(R.id.tvCurrentTime);
        textViewTotalDuration = findViewById(R.id.tvTotalDuration);
        handler = new Handler();

        // Set click listeners
        buttonPlay.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });

        buttonPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        buttonStop.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Method to update seekbar progress
    private void updateSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = mediaPlayer.getCurrentPosition();
            int totalDuration = mediaPlayer.getDuration();
            seekBar.setProgress((int) (((double) currentPosition / totalDuration) * 100));
            handler.postDelayed(this::updateSeekBar, 1000); // Update every second
            updateTextViewCurrentTime(currentPosition); // Update current time
        }
    }

    // Method to update text view for current time
    private void updateTextViewCurrentTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = ((milliseconds / (1000 * 60)) % 60);
        int hours = ((milliseconds / (1000 * 60 * 60)) % 24);
        String timeString;
        if (hours == 0) {
            timeString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        } else {
            timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        }
        textViewCurrentTime.setText(timeString);
    }

    // Method to update text view for total duration
    private void updateTextViewTotalDuration() {
        int milliseconds = mediaPlayer.getDuration();
        int seconds = (milliseconds / 1000) % 60;
        int minutes = ((milliseconds / (1000 * 60)) % 60);
        int hours = ((milliseconds / (1000 * 60 * 60)) % 24);
        String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        textViewTotalDuration.setText(timeString);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        // Update seekbar secondary progress while buffering
        seekBar.setSecondaryProgress(percent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
