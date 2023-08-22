package com.example.assignmentcheungwa;

import static androidx.core.content.ContextCompat.startActivity;

import static com.example.assignmentcheungwa.GameView.score;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.zip.Deflater;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        gameView = new GameView(this, point.x, point.y);
        setContentView(gameView);


    }
    protected void onStart() {
        super.onStart();
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat wic = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        if(wic != null)
        {
            wic.hide(WindowInsetsCompat.Type.statusBars());
            wic.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }

    }

    @Override
    protected void onStop() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView()).show(WindowInsetsCompat.Type.systemBars());
        super.onStop();
    }

}
