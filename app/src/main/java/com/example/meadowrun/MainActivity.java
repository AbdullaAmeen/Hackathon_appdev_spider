package com.example.meadowrun;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Display;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Display display= getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        view = new  GameView(this, size.x, size.y, v);
        setContentView(view);
        //view.addView();



    }

}
