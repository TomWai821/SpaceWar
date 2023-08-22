package com.example.assignmentcheungwa;

import android.content.Intent;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    private long tickCount = 0;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running = false;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        gameView.initGame();
        while (running) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    tickCount++;
                    gameView.update();
                    gameView.render(canvas);
                    gameView.sleep();
                }
            } catch (Exception ex) {
            } finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
