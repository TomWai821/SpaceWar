package com.example.assignmentcheungwa.GameObject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignmentcheungwa.R;

public class Background {

    public int x;
    public int y;
    public Bitmap background;

    public Background(int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.space);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }

}
