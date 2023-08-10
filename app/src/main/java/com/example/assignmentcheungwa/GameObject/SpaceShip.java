package com.example.assignmentcheungwa.GameObject;

import static com.example.assignmentcheungwa.GameView.screenRatioX;
import static com.example.assignmentcheungwa.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignmentcheungwa.R;

public class SpaceShip {
    public int x;
    public int y;
    int width;
    public int height;
    public Bitmap spaceship;
    public boolean alive ;
    public SpaceShip(int screenY, Resources res) {
        spaceship = BitmapFactory.decodeResource(res, R.drawable.spaceship);

        width = spaceship.getWidth();
        height = spaceship.getHeight();

        width *= (int) screenRatioX * 0.65;
        height *= (int) screenRatioY * 0.65;

        spaceship = Bitmap.createScaledBitmap(spaceship, width, height, false);

        y = screenY / 2 - 200;
        x = (int) (0 * screenRatioX);
    }
}
