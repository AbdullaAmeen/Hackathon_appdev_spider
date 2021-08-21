package com.example.meadowrun.GameObjects;

public class Ball {
    public static final int BALL_RADIUS = 50 , BALL_THROW_SPEEDY = -60*60, BALL_GRAVITY= 3; //down is positive



    private Sphere sphere;

    private float speedy;
    private boolean inAir;


    public Ball(float centreX, float centreY) {
        sphere = new Sphere(BALL_RADIUS, centreX,centreY);
        inAir = false;
    }

    public void ballBounce(float fps){
        speedy = BALL_THROW_SPEEDY;
        inAir = true;
    }

    public void ballUpdate(float top, long fps){
        speedy += BALL_GRAVITY*fps;
        sphere.offset(0,speedy/fps);

        if(sphere.getCentreY() + sphere.getRadius() >= top) {
            inAir = false;
            sphere.setCentreY(top - sphere.getRadius()/2);
        }
    }

    public float getRadius() {
        return sphere.getRadius();
    }

    public float getSpeedy() {
        return speedy;
    }

    public float getCentreX() {
        return sphere.getCentreX();
    }

    public float getCentreY() {
        return sphere.getCentreY();
    }

    public boolean isInAir() {
        return inAir;
    }

    public Sphere getSphere() {
        return sphere;
    }
}
