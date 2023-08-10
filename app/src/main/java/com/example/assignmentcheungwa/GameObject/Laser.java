package com.example.assignmentcheungwa.GameObject;

import static com.example.assignmentcheungwa.GameView.screenRatioX;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Space;

import com.example.assignmentcheungwa.R;

public class Laser {

    public int x;
    public int y;
    public int width;
    public int height;
    public Bitmap laser;
    public Laser(Resources res) {
        laser = BitmapFactory.decodeResource(res, R.drawable.laser);

        width = laser.getWidth() / 2;
        height = laser.getHeight() / 2;

        laser = Bitmap.createScaledBitmap(laser, width, height, true);
    }

    public Bitmap getLaser(){
        return laser;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
