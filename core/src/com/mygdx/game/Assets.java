package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;


public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;
    // singleton: prevent instantiation from other classes
    private Assets() {}

    public void init (AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // create game resource objects
        fonts=new AssetFonts();
    }

    @Override
    public void dispose () {
        assetManager.dispose();
        fonts.defaultBig.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultSmall.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
    }

    public AssetFonts fonts;

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts () {
            // create three fonts using Libgdx's 15px bitmap font
            defaultSmall = new BitmapFont(
                    Gdx.files.internal("images/arial-15.fnt"),true);
            defaultNormal = new BitmapFont(
                    Gdx.files.internal("images/arial-15.fnt"), true);
            defaultBig = new BitmapFont(
                    Gdx.files.internal("images/arial-15.fnt"), true);
            // set font sizes
            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);
            // enable linear texture filtering for smooth fonts
            defaultSmall.getRegion().getTexture().setFilter(
                    Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(
                    Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(
                    Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }
}