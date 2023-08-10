package com.example.assignmentcheungwa;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignmentcheungwa.Button.ButtonDown;
import com.example.assignmentcheungwa.Button.ButtonUp;
import com.example.assignmentcheungwa.GameObject.SpaceShip;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameView(this, point.x, point.y);

        setContentView(gameView);
        while (GameView.alive == false){
            startActivity(new Intent(GameActivity.this, GameOver.class));
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }
}
