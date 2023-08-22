package com.example.assignmentcheungwa.GameObject;

import static com.example.assignmentcheungwa.GameView.screenRatioX;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.assignmentcheungwa.R;

import java.util.Random;

public class Laser{

    public int x , y;
    public int width,height;
    public Bitmap laser;
    public Laser(Resources res) {
        laser = BitmapFactory.decodeResource(res, R.drawable.laser);

        width = laser.getWidth();
        height = laser.getHeight();

        width /= 2;
        height /= 2;

        laser = Bitmap.createScaledBitmap(laser, width, height, true);
    }

    public Bitmap getLaser(){
        return laser;
    }

    public Rect getCollisionShape(){
        return new Rect(x, y, x+ width, y+ height);
    }
}
