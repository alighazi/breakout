package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class PongLevel {
    public static final String TAG = PongLevel.class.getName();

    // objects
    public Raket player1, player2;
    public Wall left,right;
    public Ball ball;

    public PongLevel() {
        init();
    }

    private void init() {
        player1 = new Raket(Color.BLUE);
        player2 = new Raket(Color.RED);
        ball = new Ball();
        resetPositions();
    }
    void resetPositions(){
        float centerX=Constants.VIEWPORT_WIDTH/2;
        float centerY=Constants.VIEWPORT_HEIGHT/2;
        player1.position.set(centerX-player1.origin.x,0);
        player2.position.set(centerX-player2.origin.x,Constants.VIEWPORT_HEIGHT-player1.dimension.y);
        left=new Wall(0.1f,Constants.VIEWPORT_HEIGHT);
        right=new Wall(0.1f,Constants.VIEWPORT_HEIGHT);
        left.position.set(0,0);
        right.position.set(Constants.VIEWPORT_WIDTH - right.dimension.x, 0);
        left.init();
        right.init();
        resetBall();
    }
    void resetBall(){
        float centerX=Constants.VIEWPORT_WIDTH/2;
        float centerY=Constants.VIEWPORT_HEIGHT/2;
        ball.position.set(centerX,centerY);
        int angle = MathUtils.randomSign() * MathUtils.random(10, 170);
        ball.velocity.set(ball.terminalVelocity.x * MathUtils.cosDeg(angle), ball.terminalVelocity.y * MathUtils.sinDeg(angle));
    }

    public void render(SpriteBatch batch) {
        player1.render(batch);
        player2.render(batch);
        ball.render(batch);
        //left.render(batch);
        //right.render(batch);
    }

    public void update(float deltaTime) {
        ball.update(deltaTime);
        player1.update(deltaTime);
        player2.update(deltaTime);
    }
}