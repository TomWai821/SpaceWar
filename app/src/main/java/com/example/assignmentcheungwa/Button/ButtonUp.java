package com.example.assignmentcheungwa.Button;

import static com.example.assignmentcheungwa.GameView.screenRatioX;
import static com.example.assignmentcheungwa.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static com.example.assignmentcheungwa.GameView.screenX;
import static com.example.assignmentcheungwa.GameView.screenY;

import com.example.assignmentcheungwa.R;

public class ButtonUp {
    public int x;
    public int y;
    public int width;
    public int height;
    public Bitmap btnUp;
    public ButtonUp(Resources res) {
        btnUp = BitmapFactory.decodeResource(res, R.drawable.btnup);

        width = btnUp.getWidth();
        height = btnUp.getHeight();

        width *= 1.35;
        height *= 1.35;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        btnUp = Bitmap.createScaledBitmap(btnUp, width, height, false);

        x = 0;
        y = screenY / 20 * 16;
    }
}
