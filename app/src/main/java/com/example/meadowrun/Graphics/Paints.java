package com.example.meadowrun.Graphics;

import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;
/*                                        */
public class Paints {
    public static final String colorBackGround="#87ceeb",colorFloor="#567d46", colorBall="#000000", colorButton="#90EE90";
    public static final String colorButtonText="#006400",colorObstacleHead="#3A5F0B", colorObstacleBody="#53350A" ;
    Paint paintFloor;
    Paint paintBall;
    Paint paintObstacleHead, paintObstacleBody;

    public Paint getPaintButton() {
        return paintButton;
    }

    Paint paintButton;

    public Paints() {
        paintFloor = new Paint();
        paintFloor.setColor(Color.parseColor(colorFloor));

        paintBall = new Paint();
        paintBall.setColor(Color.parseColor(colorBall));

        paintObstacleHead = new Paint();
        paintObstacleHead.setColor(Color.parseColor(colorObstacleHead));


        paintButton = new Paint();
        paintButton.setColor(Color.parseColor(colorButton));

        paintObstacleBody = new Paint();
        paintObstacleBody.setColor(Color.parseColor(colorObstacleBody));


    }



    public void writeTextCenter(String text, float x, float y, Canvas canvas, int color, float fontSize) {
        android.graphics.Paint paintText = new android.graphics.Paint();
        paintText.setColor(color);
        paintText.setStyle(android.graphics.Paint.Style.FILL);
        paintText.setAntiAlias(true);
        //Typeface Arcade = ResourcesCompat.getFont(getContext(), R.font.arcaden);
        //paintText.setTypeface(Arcade);
        paintText.setTextSize(fontSize);
        float xPos = x;
        paintText.setTextAlign(android.graphics.Paint.Align.CENTER);
        float yPos =  (y - (paintText.descent() + paintText.ascent()) / 2);
        canvas.save();  //
        canvas.drawText(text, x, yPos, paintText);
        canvas.restore();
    }

    public Paint getPaintFloor() {
        return paintFloor;
    }

    public Paint getPaintBall() {
        return paintBall;
    }

    public Paint getPaintObstacleHead() {
        return paintObstacleHead;
    }

    public Paint getPaintObstacleBody() {
        return paintObstacleBody;
    }
}
