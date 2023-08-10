package com.example.assignmentcheungwa.Button;

import static com.example.assignmentcheungwa.GameView.screenRatioX;
import static com.example.assignmentcheungwa.GameView.screenRatioY;
import static com.example.assignmentcheungwa.GameView.screenX;
import static com.example.assignmentcheungwa.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.assignmentcheungwa.R;

public class ButtonDown {
    public int x;
    public int y;
    public int width;
    public int height;
    public Bitmap btnDown;
    public ButtonDown(Resources res) {

        btnDown = BitmapFactory.decodeResource(res, R.drawable.btndown);

        width = btnDown.getWidth();
        height = btnDown.getHeight();

        width *= (int) screenRatioX * 1.35;
        height *= (int) screenRatioY * 1.35;

        btnDown = Bitmap.createScaledBitmap(btnDown, width, height, true);

        x = 200;
        y = screenY / 20 * 15;

    }
}
