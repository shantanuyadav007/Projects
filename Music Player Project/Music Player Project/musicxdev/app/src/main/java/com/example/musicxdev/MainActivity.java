package com.example.musicxdev;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    MediaPlayer m;
    public void play(View view){m.start();}
    public void pause(View view){m.pause();}
    public void stop(View view){m.stop();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m= MediaPlayer.create(this,R.raw.m1);
    }
}