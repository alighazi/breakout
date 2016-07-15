package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Created by ali on 3/16/2015.
 */
public class WorldRenderer implements Disposable {
    private OrthographicCamera camera, cameraGUI;
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();
    }

    public void render() {
        renderWorld(batch);
        renderGui(batch);
    }

    private void renderWorld(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();
    }

    private void renderGuiScore(SpriteBatch batch) {
        float x = 0;
        float y = 0;
        Assets.instance.fonts.defaultSmall.draw(batch, "" + worldController.score1 + ":" + worldController.scroe2, x, y);
    }

    private void renderGuiFpsCounter(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 55;
        float y = cameraGUI.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if (fps >= 45) {
            // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }

    private void renderGui(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        // draw collected gold coins icon + text
        // (anchored to top left edge)
        renderGuiScore(batch);
        // draw FPS text (anchored to bottom right edge)
        if (GamePreferences.instance.showFpsCounter)
            renderGuiFpsCounter(batch);
        renderGuiGameOverMessage(batch);
        batch.end();
    }

    private void renderGuiGameOverMessage(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth / 2;
        float y = cameraGUI.viewportHeight / 2;
        if (worldController.isGameOver()) {
            BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
            fontGameOver.setColor(1, 0.75f, 0.25f, 1);
            String msg = "GAME OVER";
            if (worldController.score1 == Constants.MAX_SCORE)
                msg += "\nYOU WON!";
            else
                msg += "\nYOU LOST!";
            fontGameOver.draw(batch, msg, x, y, 0, Align.center,true);
            fontGameOver.setColor(1, 1, 1, 1);
        }
    }

    public void resize(int width, int height) {
        camera.viewportHeight = Constants.VIEWPORT_HEIGHT;
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height) * (float) width;
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
