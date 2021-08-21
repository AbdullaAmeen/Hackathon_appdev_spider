package com.example.meadowrun.GameObjects;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.example.meadowrun.Graphics.Paints;

public class Obstacle {

    private RectF body;
    private Sphere head;
    private int speedX;
    private boolean isOutside;

    public Obstacle(float screenX, float bottomY, int width, int height, int headRadius, int speedX) {
        float centreX = screenX + width/2;
        this.body = new RectF(centreX - width/2, bottomY - height, centreX + width/2, bottomY);
        this.head = new Sphere(headRadius,centreX,bottomY - height);
        this.speedX = (int)(-1*screenX*speedX/10);
        isOutside = false;
    }
   public boolean detectCollision(Sphere sphere){
        RectF ballHitBox = new RectF(sphere.getLeft(), sphere.getTop(), sphere.getRight(), sphere.getBottom());
        if(RectF.intersects(body,ballHitBox))
            return true;
        if(Sphere.intersects(sphere, head))
            return true;
        return false;
    }

    public void update(float fps){
        if (body.right > 0 && head.getRight()> 0) {
            body.offset(speedX/fps, 0);
            head.offset(speedX/fps, 0);
        }
        else
            isOutside = true;

    }

    public boolean isOutside() {
        return isOutside;
    }

    public void draw(Canvas canvas, Paints paints){
        canvas.save();
        Log.d("yes","Drawyes");
        canvas.drawRect(body,paints.getPaintObstacleBody());
        canvas.drawCircle(head.getCentreX(),head.getCentreY(),head.getRadius(), paints.getPaintObstacleHead());
        canvas.restore();
    }

    public RectF getBody() {
        return body;
    }
}
