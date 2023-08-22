package com.example.assignmentcheungwa;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignmentcheungwa.Button.ButtonDown;
import com.example.assignmentcheungwa.Button.ButtonShot;
import com.example.assignmentcheungwa.Button.ButtonUp;
import com.example.assignmentcheungwa.GameObject.Background;
import com.example.assignmentcheungwa.GameObject.Laser;
import com.example.assignmentcheungwa.GameObject.Meteor;
import com.example.assignmentcheungwa.GameObject.SpaceShip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    static MediaPlayer mp;
    boolean isGameOver;
    private SharedPreferences prefs;
    private MainThread thread;
    private GameActivity activity;
    public static int screenX;
    public static int screenY;
    private Random random;
    public static float screenRatioX, screenRatioY;
    private Background background1;
    private SpaceShip spaceship;
    private ButtonUp btnUp;
    private ButtonDown btnDown;
    private ButtonShot btnShot;
    Paint paint;
    private ArrayList<Laser> lasers;
    private Meteor[] meteors;
    long laserCooldown;
    public static int score;

    boolean isPlaying;
    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        lasers = new ArrayList<>();

        int meteorAmount = 4 + (score/10);
        meteors = new Meteor[meteorAmount];
        for (int i = 0; i < meteorAmount;i++) {
            Meteor meteor = new Meteor(getResources());
            meteors[i] = meteor;
        }
        random = new Random();

        btnUp = new ButtonUp(getResources());
        btnDown = new ButtonDown(getResources());
        btnShot = new ButtonShot(getResources());

        background1 = new Background(screenX,screenY,getResources());
        spaceship = new SpaceShip(getResources());
    }

    void update() {
        if (spaceship.isGoingUp)
        {
            spaceship.y += 30 * screenRatioY;
        }
        else if (spaceship.isGoingDown)
        {
            spaceship.y -= 30 * screenRatioY;
        }

        if (spaceship.y < 50) {
            spaceship.y = 50;
        }
        if (spaceship.y >= screenY - spaceship.height - 200) {
            spaceship.y = screenY - spaceship.height - 200;
        }
        List<Laser> trash = new ArrayList<>();

        for (Laser laser : lasers) {

            if (laser.x > screenX)
                trash.add(laser);

            laser.x += 50 * screenRatioX;

            for (Meteor meteor : meteors) {

                if (Rect.intersects(meteor.getCollisionShape(), laser.getCollisionShape())) {
                    score++;
                    meteor.x = -500;
                    laser.x = screenX + 500;
                    meteor.wasShot = true;
                }
            }
        }
        for (Laser laser : trash)
            lasers.remove(laser);

        for (Meteor meteor : meteors) {
            meteor.x -= meteor.speed;

            if (meteor.x + meteor.width < 0) {

                if (!meteor.wasShot) {
                    isPlaying = false;
                    return;
                }

                int bound = (int) (20 * screenRatioX);
                meteor.speed = random.nextInt(bound);

                if (meteor.speed < 10 * screenRatioX)
                    meteor.speed = (int) (10 * screenRatioX);

                meteor.x = screenX;
                meteor.y = random.nextInt((screenY-100) - meteor.height);

                meteor.wasShot = false;
            }

            if (Rect.intersects(spaceship.getCollisionShape(), meteor.getCollisionShape())) {
                isGameOver = true;
                return;
            }
        }
        laserCooldown();
    }

    public void render(Canvas canvas) {
        invalidate();
        canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
        canvas.drawBitmap(spaceship.spaceship, spaceship.x, spaceship.y, paint);

        canvas.drawBitmap(btnUp.btnUp, btnUp.x, btnUp.y, paint);
        canvas.drawBitmap(btnDown.btnDown, btnDown.x, btnDown.y, paint);
        canvas.drawBitmap(btnShot.btnShot, btnShot.x, btnShot.y, paint);

        for (Laser laser : lasers)
            canvas.drawBitmap(laser.getLaser(), laser.x, laser.y, paint);

        for (Meteor meteor : meteors)
            canvas.drawBitmap(meteor.getMeteor(), meteor.x, meteor.y, paint);

        Paint paint1 = new Paint();
        paint1.setTextSize(80);
        paint1.setColor(Color.WHITE);
        canvas.drawText("Score: " + score, 15f, 60f, paint1);

        if (isGameOver) {
            isPlaying = false;
            saveIfHighScore();
            waitBeforeExiting();
            thread.setRunning(false);
            return;
        }

    }

    void sleep(){
        try{
            Thread.sleep(20);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while(retry)  {
            try{
                thread.setRunning(true);
                thread.join();
                retry  = false;
            } catch(InterruptedException e)
            {

            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x > btnDown.x && x < btnDown.x + btnDown.width && y > btnDown.y && y < btnDown.y + btnDown.height) {
                    spaceship.isGoingUp = true;
                    spaceship.isGoingDown = false;
                }
                else if (x > btnUp.x && x < btnUp.x + btnUp.width && y > btnUp.y && y < btnUp.y + btnUp.height) {
                    spaceship.isGoingUp = false;
                    spaceship.isGoingDown = true;
                }
                if (x > btnShot.x && x < btnShot.x + btnShot.width && y > btnShot.y && y < btnShot.y + btnShot.height) {
                        newBullet();

                }
                break;
        }
        return false;
    }

    protected void initGame(){
        score = 0;
        isPlaying = true;
    }
    protected void laserCooldown(){
        if(laserCooldown > 0) {
            laserCooldown -= 1;
        } else{
            laserCooldown = 0;
        }
    }

    public void newBullet() {
        while (laserCooldown == 0) {
            mp = MediaPlayer.create(getContext(), R.raw.laser);
            mp.start();

            Laser laser1 = new Laser(getResources());
            laser1.x = spaceship.x + spaceship.width;
            laser1.y = spaceship.y + (spaceship.height / 2);
            lasers.add(laser1);
            laserCooldown = 10;
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(1000);
            Intent i = new Intent();
            i.putExtra("returnKey1", "1111");
            i.getAction();
            activity.setResult(RESULT_OK, i);
            activity.finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {

        if (prefs.getInt("HighestScore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("HighestScore", score);
            editor.apply();
        }
    }
}
