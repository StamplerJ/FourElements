package com.beemelonstudio.fourelements.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beemelonstudio.fourelements.FourElements;
import com.beemelonstudio.fourelements.utils.Assets;

/**
 * Created by Stampler on 20.09.17.
 */

public class PlayScreenUI extends GameUIScreen {

    PlayScreen screen;

    TextureAtlas textureAtlas;

    Label score;
    Image healthBar;
    TextButton pauseButton;

    public PlayScreenUI(final PlayScreen screen){
        super(screen);

        this.screen = screen;

        textureAtlas = (TextureAtlas) Assets.get("uiTextureAtlas");

        score = new Label("Score: ", skin);
        score.setPosition(10, FourElements.W_HEIGHT - 50);
        stage.addActor(score);

        healthBar = new Image();
        healthBar.setDrawable(new TextureRegionDrawable( textureAtlas.findRegion("healthbar") ));
        healthBar.setBounds(0, FourElements.W_HEIGHT - 20, FourElements.W_WIDTH, 20);
        stage.addActor(healthBar);

        pauseButton = new TextButton("Pause", skin);
        pauseButton.setPosition(FourElements.W_WIDTH - pauseButton.getWidth() - 10, FourElements.W_HEIGHT - 50);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                screen.running ^= true;
            }
        });
        stage.addActor(pauseButton);
    }

    public void act(float delta) {
        score.setText("Score: " + screen.score);
        healthBar.setWidth((float) screen.city.health / 100f * (float) FourElements.W_WIDTH);
    }
}