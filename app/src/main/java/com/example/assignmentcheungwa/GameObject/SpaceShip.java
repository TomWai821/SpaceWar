package com.example.assignmentcheungwa.GameObject;

import static com.example.assignmentcheungwa.GameView.screenRatioX;
import static com.example.assignmentcheungwa.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.assignmentcheungwa.R;

public class SpaceShip{
    public int x,y;
    public int width,height;
    public Bitmap spaceship;
    public static boolean isGoingUp, isGoingDown;
    public SpaceShip(Resources res) {

        spaceship = BitmapFactory.decodeResource(res, R.drawable.spaceship);
        width = spaceship.getWidth();
        height = spaceship.getHeight();

        width = (int) (width*0.65);
        height = (int) (height * 0.65);

        spaceship = Bitmap.createScaledBitmap(spaceship, width, height, false);

        x = (int) (0 * screenRatioX);
        y = (int) (screenRatioY/3);
    }
    public Rect getCollisionShape(){
        return new Rect(x, y, x+ width, y+ height);
    }
}
