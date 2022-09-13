package com.example.app30;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    MediaPlayer mp;
    SeekBar sb;
    AudioManager am;
    Button btnStartGameVS_Computer;
    Button btnStartGameVS_Friend;
    Button btnOpenYoutubeVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenYoutubeVideo = findViewById(R.id.btnOpenYoutubeVideo);
        btnOpenYoutubeVideo.setOnClickListener(this);
        btnStartGameVS_Friend = findViewById(R.id.btnStartGameVS_Friend);
        btnStartGameVS_Friend.setOnClickListener(this);
        btnStartGameVS_Computer = findViewById(R.id.btnStartGameVS_Computer);
        btnStartGameVS_Computer.setOnClickListener(this);

        sb=(SeekBar) findViewById(R.id.sb);
        mp=MediaPlayer.create(this, R.raw.bgmusic);
        mp.start();


        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max= am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        sb.setMax(max);
        sb.setProgress(max/6);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, max/6, 0);
        sb.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnOpenYoutubeVideo){
            String videoId = "OmC07DvEayY";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            intent.putExtra("VIDEO_ID", videoId);
            startActivity(intent);
        }
        else if(v == btnStartGameVS_Friend){
            Intent intent = new Intent(MainActivity.this, ticTacToeVS_Friend.class);
            startActivity(intent);
            Toast.makeText(this, "ddd", Toast.LENGTH_SHORT).show();
        }
        else if(v == btnStartGameVS_Computer){
            Intent intent = new Intent(MainActivity.this, ticTacTowVS_computer.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int idMenu = item.getItemId();

        if(idMenu == R.id.setProfile){
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
            return true;
        }

        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}