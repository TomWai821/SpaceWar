package com.example.assignmentcheungwa.GameObject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.assignmentcheungwa.GameView;
import com.example.assignmentcheungwa.R;

import java.util.Random;

public class Meteor{
    public int x,y, speed = 2 + (GameView.score/5);
    public boolean wasShot = true;
    public int width, height;
    public static Bitmap meteor;
    Random random;
    public Meteor(Resources res) {
        meteor = BitmapFactory.decodeResource(res, R.drawable.meteor);

        width = meteor.getWidth() * 3;
        height = meteor.getHeight() * 2;

        x = 1500;
        if(y < 100){
            y = 100;
        }if(y > 700){
            y = 700;
        }

        meteor = Bitmap.createScaledBitmap(meteor, width, height, true);
    }

    public Bitmap getMeteor(){ return meteor; }

    public Rect getCollisionShape(){
        return new Rect(x, y, x+ width, y+ height);
    }
}
