package com.example.assignmentcheungwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvStart,tvContent,tvQuit,tvScores;
    ImageButton btnUnMute;
    MediaPlayer mediaPlayer;
    public boolean mute = false;
    public int HighestScore = 0;
    public int currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvStart = findViewById(R.id.tvStart);
        btnUnMute = findViewById(R.id.btnUnMute);
        tvContent = findViewById(R.id.tvContent);
        tvQuit = findViewById(R.id.tvQuit);
        tvScores = findViewById(R.id.tvScores);

        if(mute == false) {
            play();
        }
        else if(mute == true) {
            stop();
        }

        btnUnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mute == false) {
                    play();
                    mute = true;
                }
                else if(mute == true) {
                    stop();
                    mute = false;
                }
            }

        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        tvContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event)
            {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:12345678")));
                return false;
            }
        });

        tvQuit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event)
            {
                finish();
                return false;
            }
        });

        tvScores.setText(""+ HighestScore);
        if(currentScore > HighestScore)
        {
            currentScore = HighestScore;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        SharedPreferences sp = getSharedPreferences("Idk.txt",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Bool",mute);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("Idk.txt",0);
        mute = sp.getBoolean("Idk.txt",true);
    }

    public void play(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.starwar);
        btnUnMute.setImageResource(R.drawable.unmute);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    public void stop(){
        btnUnMute.setImageResource(R.drawable.mute);
        mediaPlayer.stop();
    }
}
