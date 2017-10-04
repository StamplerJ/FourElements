package com.beemelonstudio.fourelements.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.fourelements.FourElements;

import java.util.Stack;

/**
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class GameScreen implements Screen, InputProcessor {

    protected FourElements game ;
    protected Stack<GameScreen> screens;

    protected SpriteBatch batch;
    protected OrthographicCamera camera, hudCamera;
    protected Viewport viewport;

    protected Stage stage;
    protected Skin skin;

    private boolean shown = false;
    private boolean backButtonLocked = false;

    public GameScreen(FourElements game) {
        this.game = game;
        this.screens = game.screens;
        this.batch = game.batch;
        this.camera = game.camera;
        this.viewport = game.viewport;
        this.stage = game.stage;
        this.skin = game.skin;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public void show() {
        stage.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Catch Androids native back button
        handleBackButton();

        viewport.apply();

        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, false);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // TODO: Do I dispose this?
        batch.dispose();
        stage.dispose();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private void handleBackButton(){

        if(!Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            backButtonLocked = false;
            return;
        }

        if(backButtonLocked)
            return;

        Gdx.app.log("size", ""+screens.size());

        if(screens.size() > 1){

            screens.pop();
            game.setScreen(screens.peek());
        }
        else {

            // Only show the dialog once;
            if(shown)
                return;

            shown = true;

            // Create "Are you sure" dialog
            final Dialog dialog = new Dialog("", skin, "dialog"){
                @Override
                protected void result(Object object) {
                    super.result(object);
                    if( (Boolean) object ){
                        shown = false;
                        Gdx.app.exit();
                    }
                    else {
                        shown = false;
                        hide();
                    }
                }
            };

            dialog.setBounds( game.W_WIDTH / 10, game.W_HEIGHT / 2, game.W_WIDTH / 10 * 8, game.W_HEIGHT / 10 * 4);
            dialog.text("Are you sure you want to quit?");
            dialog.button("Yes", true);
            dialog.button("No", false);

            dialog.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if( x < dialog.getX() || x > dialog.getX() + dialog.getWidth()
                            || y < dialog.getY() || y > dialog.getY() + dialog.getHeight() ) {
                        shown = false;
                        dialog.hide();
                    }
                }
            });
            stage.addActor(dialog);
        }
        backButtonLocked = true;
    }
}
