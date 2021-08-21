package com.example.meadowrun;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.meadowrun.GameObjects.Ball;
import com.example.meadowrun.GameObjects.Obstacle;
import com.example.meadowrun.Graphics.Paints;
import com.example.meadowrun.UIElements.Button;

import java.util.Random;

public class GameView extends SurfaceView {
    SurfaceHolder holder;
    GameThread gameThread;
    RectF screen;
    Vibrator vibrator;
    Context context;

    Button button_play;
    Button button_restart;

    Ball ball;
    Obstacle obstacle;

    RectF graphics_floor;
    final int FLOOR_HEIGHT = 300;
    final int FLOOR_OFFSET = 15;

    Paints paints = new Paints();

    public void setFps(long fps) {
        this.fps = fps;
    }

    private long fps=60;
    private int timePassed=0;

    public GameView(Context context, float sWidth, float sHeight, Vibrator vibrator) {
        super(context);
        this.context = context;
        screen = new RectF(0,0,sWidth,sHeight);
        this.vibrator = vibrator;
        setGameProperties();
        gameThread = new GameThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                gameThread.gameMenu();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                boolean retry = true;
                gameThread.setGameState(GameThread.STATE_GAME_OVER);
                while (retry) {
                    try {
                        gameThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });




    }
    public void setGameProperties(){

        graphics_floor = new RectF(0,screen.bottom- FLOOR_HEIGHT,screen.right,screen.bottom);
        button_play = new Button(screen.centerX(),(screen.bottom-FLOOR_HEIGHT)/2,"PLAY");
        button_restart = new Button(screen.centerX(),(screen.bottom-FLOOR_HEIGHT)/2,"RESTART");
        ball = new Ball(screen.width()/10, graphics_floor.top - Ball.BALL_RADIUS + FLOOR_OFFSET);
        obstacle = new Obstacle(screen.width(),graphics_floor.top + FLOOR_OFFSET, 100, 250, 120, 5 );

    }
    public void drawGameMenu(Canvas canvas){
        if (canvas != null) {
            //background
            canvas.drawColor(Color.parseColor(Paints.colorBackGround));

            //draw floor
            canvas.drawRect(graphics_floor, paints.getPaintFloor());

            //draw Button
            button_play.drawButton(canvas);

            paints.writeTextCenter("HighScore: "+ getDataInt(),screen.centerX(),(screen.bottom-FLOOR_HEIGHT)/3,canvas,Color.parseColor(Paints.colorButtonText),40);


        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (gameThread.getGameState() == GameThread.STATE_START) {
            if (button_play.getButton().contains((int) x, (int) y)) {
                performClick();
                gameThread.setGameState(GameThread.STATE_IN_GAME);
                gameThread.start();
                gameThread.setGameStartTime(System.currentTimeMillis());
            }
        }
        else if(gameThread.getGameState() == GameThread.STATE_IN_GAME) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!ball.isInAir())
                        ball.ballBounce(fps);


            }
        }
        else if(gameThread.getGameState() == GameThread.STATE_GAME_OVER){
            if (button_restart.getButton().contains((int) x, (int) y)) {
                performClick();
                setGameProperties();
                gameThread.setGameState(GameThread.STATE_GAME_OVER);
                gameThread = new GameThread(this);
                gameThread.setGameState(GameThread.STATE_IN_GAME);
                gameThread.setGameStartTime(System.currentTimeMillis());
                gameThread.start();
            }
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null && gameThread.getGameState()==GameThread.STATE_IN_GAME) {
            long gameCurrentTime = System.currentTimeMillis();
            timePassed = (int)((gameCurrentTime- gameThread.getGameStartTime())/1000);

            Log.d("fps",""+fps);
            //background
            canvas.drawColor(Color.parseColor(Paints.colorBackGround));

            //time
            paints.writeTextCenter("Time: "+timePassed, screen.width()-100, 100, canvas, Color.parseColor(Paints.colorButtonText),30 );
            //draw floor
            canvas.drawRect(graphics_floor, paints.getPaintFloor());
            //update ball
            if(ball.isInAir())
                ball.ballUpdate(graphics_floor.top, fps);
            //draw ball
            canvas.drawCircle(ball.getCentreX(), ball.getCentreY(), ball.getRadius(), paints.getPaintBall());


            //obstacle
            if(!obstacle.isOutside()) {
                Log.d("yes","yes");
                obstacle.update(fps);
                obstacle.draw(canvas,paints);

            }
            else
                obstacle = new Obstacle(screen.width(),graphics_floor.top + FLOOR_OFFSET, 100, 250, 120, 5+new Random().nextInt(1+timePassed/10));

            if(obstacle.detectCollision(ball.getSphere())) {
                Log.d("gameover","gameOver");
                drawGameOverMenu(canvas);
                gameThread.setGameState(GameThread.STATE_GAME_OVER);
            }


        }
    }



    public void saveDataInt(int data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HIGH_SCORE",data);
        editor.commit();
    }

    public int getDataInt() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("HIGH_SCORE", 0);
    }

    public void drawGameOverMenu(Canvas canvas) {
        if(getDataInt()>timePassed)
            saveDataInt(timePassed);

        if(canvas != null){
                    //background
                    Log.d("namea","name");
                    canvas.drawColor(Color.parseColor(Paints.colorBackGround));
                     paints.writeTextCenter("HighScore: "+ getDataInt(),screen.centerX(),(int)((screen.bottom-FLOOR_HEIGHT)/3),canvas,Color.parseColor(Paints.colorButtonText),40);
                    paints.writeTextCenter("Score: "+ timePassed,screen.centerX(),(screen.bottom-FLOOR_HEIGHT)/4,canvas,Color.parseColor(Paints.colorButtonText),40);
                    //draw floor
                    canvas.drawRect(graphics_floor, paints.getPaintFloor());

                    //draw Button
                    button_restart.drawButton(canvas);


                }
            }



}
