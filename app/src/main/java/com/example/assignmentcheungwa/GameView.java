package com.example.assignmentcheungwa;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.assignmentcheungwa.Button.ButtonDown;
import com.example.assignmentcheungwa.Button.ButtonShot;
import com.example.assignmentcheungwa.Button.ButtonUp;
import com.example.assignmentcheungwa.GameObject.Background;
import com.example.assignmentcheungwa.GameObject.Laser;
import com.example.assignmentcheungwa.GameObject.SpaceShip;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable{

    static MediaPlayer mp;
    Thread thread;
    private boolean isPlaying;
    public static int screenX;
    public static int screenY;
    public static float screenRatioX, screenRatioY;
    private Background background1;
    private SpaceShip spaceship;
    private Laser laser;
    private ButtonUp btnUp;
    private ButtonDown btnDown;
    private ButtonShot btnShot;
    Paint paint;
    Canvas canvas;
    ArrayList<Laser> ourLaser;
    long UPDATE_MILLIS = 30;
    boolean pause = false;;
    long cooldown;
    static int score;
    static boolean alive = true;
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;
        ourLaser = new ArrayList<>();

        btnUp = new ButtonUp(getResources());
        btnDown = new ButtonDown(getResources());
        btnShot = new ButtonShot(getResources());

        background1 = new Background(screenX,screenY,getResources());
        spaceship = new SpaceShip(screenY, getResources());
        Paint paint = new Paint();
    }

    @Override
    public void run()
    {
        while(isPlaying) {
            update();
            draw();
            sleep();
        }
        if(!pause){
            getHandler().postDelayed(this::run,UPDATE_MILLIS);
        }
    }

    void update(){
        while(spaceship.y < -50)
        {
            spaceship.y = -50;
        }

        while(spaceship.y > screenY - spaceship.height - 200)
        {
            spaceship.y = screenY - spaceship.height - 200;
        }
        if(cooldown > 0) {
            cooldown -= 1;
        }else{
            cooldown = 0;
        }
    }

    public void draw(){
        invalidate();
        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(spaceship.spaceship, spaceship.x, spaceship.y, paint);

            canvas.drawBitmap(btnUp.btnUp, btnUp.x, btnUp.y, paint);
            canvas.drawBitmap(btnDown.btnDown, btnDown.x, btnDown.y, paint);
            canvas.drawBitmap(btnShot.btnShot, btnShot.x, btnShot.y, paint);

            for (int i = 0; i < ourLaser.size(); i++) {
                int x1 = ourLaser.get(i).x + 250;
                int y1 = ourLaser.get(i).y + spaceship.y + 125;
                ourLaser.get(i).x += 100;
                canvas.drawBitmap(ourLaser.get(i).getLaser(), x1, y1, paint);
            }
            getHolder().unlockCanvasAndPost(this.canvas);
        }
    }

    private void sleep(){
        try{
            Thread.sleep(17);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try{
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x > btnDown.x && x < btnDown.x + btnDown.width && y > btnDown.y && y < btnDown.y + btnDown.height) {
                    spaceship.y += 30 * screenRatioY;
                } else if (x > btnUp.x && x < btnUp.x + btnUp.width && y > btnUp.y && y < btnUp.y + btnUp.height) {
                    spaceship.y -= 30 * screenRatioY;
                }
                if (x > btnShot.x && x < btnShot.x + btnShot.width && y > btnShot.y && y < btnShot.y + btnShot.height) {
                    while (cooldown == 0) {
                        mp = MediaPlayer.create(getContext(), R.raw.laser);
                        mp.start();
                        Laser laser1 = new Laser(getResources());
                        ourLaser.add(laser1);
                        cooldown = 10;
                    }
                }
                break;
        }
        return false;
    }
}
