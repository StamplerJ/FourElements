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
 * Created by Stampler on 18.09.17.
 */

public class SettingsScreen extends GameScreen {

    private Table table;

    private Label infoLabel;
    private TextButton backButton;

    public SettingsScreen(FourElements game) {
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
        infoLabel = new Label("This is the settings screen \n Nothing's happening in here", skin);
        infoLabel.setBounds( FourElements.W_WIDTH/2 - FourElements.W_WIDTH/4, FourElements.W_HEIGHT/3, FourElements.W_WIDTH/2, FourElements.W_HEIGHT/7);
        infoLabel.setColor(0f, 0f, 0f, 1f);
        infoLabel.setAlignment(Align.center);
        infoLabel.setFontScale(1.1f);
        stage.addActor(infoLabel);

        backButton = new TextButton("Back", skin);
        backButton.setBounds( infoLabel.getX(), infoLabel.getY() - infoLabel.getHeight() - 10, infoLabel.getWidth(), infoLabel.getHeight());
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.screens.pop();
                game.setScreen(game.screens.peek());
            }
        });
        stage.addActor(backButton);

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
