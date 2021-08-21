package com.example.meadowrun;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;

public class GameThread extends Thread {
    final static int STATE_START = -1, STATE_IN_GAME=1, STATE_GAME_OVER=0, STATE_PAUSE=-2;
    Canvas canvas = null;

    private GameView view;

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    private int gameState;

    private long gameStartTime;
    private long gameEndTime;

    public long getGameStartTime() {
        return gameStartTime;
    }

    public long getGameEndTime() {
        return gameEndTime;
    }

    public void setGameStartTime(long gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public void setGameEndTime(long gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

    public GameThread(GameView view) {
        this.view = view;
        gameState = STATE_START;
    }

    public void gameMenu(){
        try{
            canvas = view.getHolder().lockCanvas();
            synchronized (view.getHolder()) {
                view.drawGameMenu(canvas);
            }
        }
        finally {
            if(canvas != null)
                view.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void gameOverMenu(){
        Canvas canvas = new Canvas();
        try{
            canvas = view.getHolder().lockCanvas();
            synchronized (view.getHolder()) {
                view.drawGameOverMenu(canvas);
            }
        }
        finally {
            if(canvas != null)
                view.getHolder().unlockCanvasAndPost(canvas);
        }
    }


    @SuppressLint("WrongCall")
    @Override
    public void run() {


        while(gameState == STATE_IN_GAME ){
            long startFrameTime = System.currentTimeMillis();

                    try {
                        canvas = view.getHolder().lockCanvas();
                        synchronized (view.getHolder()) {
                            view.onDraw(canvas);

                        }
                    } finally {
                        if (canvas != null) {
                            view.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }

                    long lastFrameTime = System.currentTimeMillis();
                    long timeThisFrame = lastFrameTime - startFrameTime;
                    Log.d("fps","lft"+timeThisFrame);
                    if (timeThisFrame >= 1) {
                        view.setFps(1000 / timeThisFrame);
                    }
                }


                }
            }


