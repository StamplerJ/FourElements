package com.beemelonstudio.fourelements.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.beemelonstudio.fourelements.FourElements;

/**
 * Created by Stampler on 30.08.17.
 */

public class GameOverScreen extends GameScreen{

    Table table;
    Label scoreLabel;
    TextButton restartButton;

    public GameOverScreen(FourElements game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        stage.clear();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Turn table debugging on
        table.setDebug(true);

        // Widgets
        scoreLabel = new Label("Score: " + game.preferences.getInteger("currentScore", 0), skin);
        scoreLabel.setBounds(FourElements.W_WIDTH/2 - FourElements.W_WIDTH/4, FourElements.W_HEIGHT/1.5f, FourElements.W_WIDTH/2, FourElements.W_HEIGHT/7);
        scoreLabel.setAlignment(Align.center);
        stage.addActor(scoreLabel);
        
        restartButton = new TextButton("Restart", skin);
        restartButton.setBounds(game.W_WIDTH/2 - game.W_WIDTH/4, game.W_HEIGHT/3, game.W_WIDTH/2, game.W_HEIGHT/7);
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.screens.pop();
                game.screens.push(new PlayScreen(game));
                game.setScreen(game.screens.peek());
            }
        });
        stage.addActor(restartButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
