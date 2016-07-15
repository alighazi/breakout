package com.mygdx.game;

import com.badlogic.gdx.*;

/**
 * Created by ali on 3/16/2015.
 */
public class WorldController extends InputAdapter {
    private static final String TAG = "GDXGAME";
    public PongLevel level;
    public int score1, scroe2;
    public Game game;

    public void initLevel() {
        level = new PongLevel();
        score1 = scroe2 = 0;
    }

    public WorldController(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        score1 = scroe2 = 0;
        timeLeftGameOverDelay = 5;
        initLevel();
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        if (isGameOver()) {
            timeLeftGameOverDelay -= deltaTime;
            if (timeLeftGameOverDelay < 0) backToMenu();
        } else {
            handleInputGame(deltaTime);
            handleAi(deltaTime);
            level.update(deltaTime);
            keepplayersInField();
            testCollisions();
            checkgoal();
        }
    }

    private void handleAi(float deltaTime) {
        Ball b = level.ball;
        Raket r = level.player2;
        float deltaX = b.position.x + b.origin.x -(r.position.x + r.origin.x);
        if (deltaX > r.origin.x)
            r.acceleration.set(30, 0);
        else if (deltaX <= -r.origin.x)
            r.acceleration.set(-30, 0);
        else r.acceleration.set(0,0);
    }

    private void keepplayersInField() {
        if (level.player2.position.x <= 0)
            level.player2.position.x = 0;
        if (level.player1.position.x <= 0)
            level.player1.position.x = 0;
        if (level.player1.position.x > Constants.VIEWPORT_WIDTH - level.player1.dimension.x) {
            level.player1.position.x = Constants.VIEWPORT_WIDTH - level.player1.dimension.x;
        }
        if (level.player2.position.x > Constants.VIEWPORT_WIDTH - level.player2.dimension.x) {
            level.player2.position.x = Constants.VIEWPORT_WIDTH - level.player2.dimension.x;
        }
    }

    private void checkgoal() {
        Ball b = level.ball;
        if (b.position.y+b.dimension.y >= Constants.VIEWPORT_HEIGHT) {
            score1++;
            level.resetBall();
        }
        if (b.position.y <= 0) {
            scroe2++;
            level.resetBall();
        }
    }

    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;
    }


    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game World Resetted");
        }
        // Back to Menu
        else if (keyCode == Input.Keys.ESCAPE || keyCode == Input.Keys.BACK) {
            backToMenu();
        }

        return false;
    }

    private void handleInputGame(float deltaTime) {
        // Player Movement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            level.player1.velocity.x = -level.player1.terminalVelocity.x;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            level.player1.velocity.x = level.player1.terminalVelocity.x;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z))
            level.player2.velocity.x = -level.player2.terminalVelocity.x;
        else if (Gdx.input.isKeyPressed(Input.Keys.X))
            level.player2.velocity.x = level.player2.terminalVelocity.x;

        // Bunny Jump
//            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//                level.player1.setJumping(true);
//            } else {
//                level.player1.setJumping(false);
//            }
    }

    // Rectangles for collision detection

    private void onCollisionRaketWithBall() {
        level.ball.velocity.set(level.ball.velocity.x, -level.ball.velocity.y);
    }

    private void onCollisionWallWithBall() {
        level.ball.velocity.set(-level.ball.velocity.x, level.ball.velocity.y);
    }

    private void testCollisions() {
        Ball b = level.ball;
        Raket p1 = level.player1, p2 = level.player2;
        if (b.velocity.y < 0.f && b.position.y <= p1.dimension.y && b.position.x >= p1.position.x && b.position.x <= p1.position.x + p1.dimension.x)
            onCollisionRaketWithBall();
        else {
            if (b.velocity.y > 0.f && b.position.y + b.dimension.y >= p2.position.y && b.position.x >= p2.position.x && b.position.x <= p2.position.x + p2.dimension.x)
                onCollisionRaketWithBall();
            else {
                if ((b.position.x + b.dimension.x >= Constants.VIEWPORT_WIDTH && b.velocity.x > 0) || (b.position.x <= 0 && b.velocity.x < 0))
                    onCollisionWallWithBall();
            }
        }
    }

    private float timeLeftGameOverDelay;

    public boolean isGameOver() {
        return score1 >= Constants.MAX_SCORE || scroe2 >= Constants.MAX_SCORE;
    }


    private void backToMenu() {
        // switch to menu screen
        //game.setScreen(new MenuScreen(game));
    }
}
