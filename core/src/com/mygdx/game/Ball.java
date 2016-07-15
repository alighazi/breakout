package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Superior Man on 3/26/2015.
 */
public class Ball extends AbstractGameObject {
    Sprite sprite;
    public Ball(){
        dimension.set(0.3f,0.3f);
        terminalVelocity.set(5,5);
        Pixmap pm=new Pixmap(128,128, Pixmap.Format.RGBA8888);
        pm.setColor(Color.WHITE);
        pm.fillCircle(pm.getWidth()/2,pm.getHeight()/2,pm.getHeight()/2);
        Texture t=new Texture(pm);
        sprite=new Sprite(t);
        sprite.setSize(dimension.x,dimension.y);
        sprite.setOrigin(sprite.getWidth()/2.0f,sprite.getHeight()/2.0f);
        origin.set(sprite.getOriginX(),sprite.getOriginY());
        bounds.set(0,0,dimension.x,dimension.y);
    }
    @Override
    public void render(SpriteBatch batch) {
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
    }
}
