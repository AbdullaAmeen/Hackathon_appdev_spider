package com.example.meadowrun.UIElements;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import com.example.meadowrun.Graphics.Paints;

public class Button {
    public static final int BUTTON_LENGTH = 300, BUTTON_WIDTH = 150;
    private RectF button;
    private String buttonText;
    Paints paint;

    public Button(float centerX, float centerY, String buttonText) {
        this.button = new RectF(centerX-(BUTTON_LENGTH)/2,centerY - BUTTON_WIDTH/2,centerX+BUTTON_LENGTH/2,centerY + BUTTON_WIDTH/2);
        this.buttonText = buttonText;
        paint = new Paints();
    }

    public RectF getButton() {
        return button;
    }


    public void drawButton(Canvas canvas) {
        canvas.drawRect(button, paint.getPaintButton());
        paint.writeTextCenter(buttonText,button.centerX(),button.centerY(), canvas, Color.parseColor(Paints.colorButtonText),button.height()/6);

    }
}
