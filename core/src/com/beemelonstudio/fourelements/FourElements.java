package com.beemelonstudio.fourelements;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beemelonstudio.fourelements.screens.GameScreen;
import com.beemelonstudio.fourelements.screens.MenuScreen;
import com.beemelonstudio.fourelements.screens.PlayScreen;
import com.beemelonstudio.fourelements.utils.Assets;

import java.util.Stack;

public class FourElements extends Game {

	public static final String TITLE = "FourElements";
	public static final int V_WIDTH = 504;
	public static final int V_HEIGHT = 896;
	public static final int W_WIDTH = 432;
	public static final int W_HEIGHT = 768;

    public Preferences preferences;
    public InputMultiplexer inputMultiplexer;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public Viewport viewport;

	public Stage stage;
	public Skin skin;

	public Stack<GameScreen> screens;

	@Override
	public void create() {
		Assets.load();

		camera = new OrthographicCamera();
		camera.setToOrtho(false); //, W_WIDTH, W_HEIGHT);

		viewport = new ExtendViewport(W_WIDTH, W_HEIGHT, camera);
		viewport.setScreenY(-viewport.getBottomGutterHeight());

		batch = new SpriteBatch();

		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));

        // Skin configurations
		skin = (Skin) Assets.get("defaultSkin");

        // TODO: Remove this, it's skin dependent
        skin.getFont("default-font").getData().setScale(1.5f);
        //skin.getFont("font").getData().setScale(0.6f);
        //skin.getFont("title").getData().setScale(0.6f);
        //skin.getFont("subtitle").getData().setScale(0.6f);

        preferences = Gdx.app.getPreferences("4Elements local highscore");

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(false);

		screens = new Stack<GameScreen>();
		//screens.push(new MenuScreen(this));
		screens.push(new PlayScreen(this));

		setScreen(screens.peek());
	}

	@Override
	public void dispose() {
		super.dispose();
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
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
