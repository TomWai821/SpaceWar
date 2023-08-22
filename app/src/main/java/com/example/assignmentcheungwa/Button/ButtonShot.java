package com.example.assignmentcheungwa.Button;

import static com.example.assignmentcheungwa.GameView.screenRatioX;
import static com.example.assignmentcheungwa.GameView.screenRatioY;
import static com.example.assignmentcheungwa.GameView.screenX;
import static com.example.assignmentcheungwa.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignmentcheungwa.R;

public class ButtonShot {

    public int x;
    public int y;
    public int width;
    public int height;
    public Bitmap btnShot;
    public ButtonShot(Resources res) {
        btnShot = BitmapFactory.decodeResource(res, R.drawable.btnshot);

        width = btnShot.getWidth();
        height = btnShot.getHeight();

        width *= 1.35;
        height *= 1.35;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;
        
        btnShot = Bitmap.createScaledBitmap(btnShot, width , height , true);

        x = screenX / 20 * 18;
        y = screenY / 20 * 16;
    }
}
