package com.example.assignmentcheungwa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    TextView tvDisplay, tvMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        tvDisplay = findViewById(R.id.tvDisplay);
        tvMenu = findViewById(R.id.tvMenu);

        tvDisplay.setText("You got "+ GameView.score + " score in this round");

        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOver.this, MainActivity.class));
            }
        });
    }
}
