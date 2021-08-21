package com.example.meadowrun.GameObjects;

public class Sphere {
    public static boolean intersects(Sphere sphere1, Sphere sphere2){
        if(Math.sqrt(sphere1.centreX*sphere1.centreX - sphere2.centreY*sphere2.centreY) <= sphere1.getRadius() + sphere2.getRadius())
            return true;
        return false;

    }

    private int radius;
    private float centreX, centreY;

    public Sphere(int radius, float centreX, float centreY) {
        this.radius = radius;
        this.centreX = centreX;
        this.centreY = centreY;
    }

    public int getRadius() {
        return radius;
    }

    public float getCentreX() {
        return centreX;
    }

    public float getCentreY() {
        return centreY;
    }
    public float getRight() {
        return centreX + radius;
    }
    public float getLeft() {
        return centreX - radius ;
    }
    public float getTop() {
        return centreY - radius;
    }
    public float getBottom() {
        return centreY + radius;
    }

    public void offset(float speedX, float speedY) {
        centreX += speedX;
        centreY += speedY;
    }

    public void setCentreY(float centreY) {
        this.centreY = centreY;
    }
}
