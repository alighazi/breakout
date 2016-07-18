package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Intersector;

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
        initLevel();
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        handleInputGame(deltaTime);
        level.update(deltaTime);
        keepplayersInField();
        Ball b = level.ball;
        Raket p1 = level.player1;
        float add=deltaTime/3;
        if (Intersector.overlaps(b.circle, level.left.sprite.getBoundingRectangle())) {
             b.undoMove(deltaTime+=add);
            b.velocity.set(-b.velocity.x, b.velocity.y);
        }else if(Intersector.overlaps(b.circle, level.right.sprite.getBoundingRectangle())) {
            b.undoMove(deltaTime+=add);
            b.velocity.set(-b.velocity.x, b.velocity.y);
        }else if (Intersector.overlaps(b.circle, p1.sprite.getBoundingRectangle())) {
            b.undoMove(deltaTime+=add);
            b.velocity.set(b.velocity.x, -b.velocity.y);
        }
        checkgoal();
    }


    private void keepplayersInField() {
        if (level.player1.position.x <= 0)
            level.player1.position.x = 0;
        if (level.player1.position.x > Constants.VIEWPORT_WIDTH - level.player1.dimension.x) {
            level.player1.position.x = Constants.VIEWPORT_WIDTH - level.player1.dimension.x;
        }
    }

    private void checkgoal() {
        Ball b = level.ball;
        if (b.position.y + b.dimension.y >= Constants.VIEWPORT_HEIGHT) {
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
        return false;
    }

    private void handleInputGame(float deltaTime) {
        // Player Movement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            level.player1.velocity.x = -level.player1.terminalVelocity.x;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            level.player1.velocity.x = level.player1.terminalVelocity.x;
        }
    }

    private void testCollisions() {

    }

    public boolean isGameOver() {
        return score1 >= Constants.MAX_SCORE || scroe2 >= Constants.MAX_SCORE;
    }

}
