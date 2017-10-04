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
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class MenuScreen extends GameScreen {

    private Table table;
    private Label highscoreLabel;
    private TextButton startButton;
    private TextButton settingsButton;

    public MenuScreen(FourElements game) {
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
        highscoreLabel = new Label("Highscore\n", skin);
        highscoreLabel.setBounds(FourElements.W_WIDTH/2 - FourElements.W_WIDTH/4, FourElements.W_HEIGHT/1.5f, FourElements.W_WIDTH/2, FourElements.W_HEIGHT/7);
        highscoreLabel.setAlignment(Align.center);
        highscoreLabel.setText(
                highscoreLabel.getText() + "" + game.preferences.getInteger("highscore", 0)
        );
        stage.addActor(highscoreLabel);

        startButton = new TextButton("Start Game", skin);
        startButton.setBounds(FourElements.W_WIDTH/2 - FourElements.W_WIDTH/4, FourElements.W_HEIGHT/3, FourElements.W_WIDTH/2, FourElements.W_HEIGHT/7);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.screens.push(new PlayScreen(game));
                game.setScreen(game.screens.peek());
            }
        });
        stage.addActor(startButton);

        settingsButton = new TextButton("Settings", skin);
        settingsButton.setBounds(FourElements.W_WIDTH/2 - FourElements.W_WIDTH/4, startButton.getY() - startButton.getHeight() - 10, FourElements.W_WIDTH/2, FourElements.W_HEIGHT/7);
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.screens.push(new SettingsScreen(game));
                game.setScreen(game.screens.peek());
            }
        });
        stage.addActor(settingsButton);
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
