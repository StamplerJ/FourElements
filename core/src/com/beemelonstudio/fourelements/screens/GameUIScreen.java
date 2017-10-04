package com.beemelonstudio.fourelements.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by Stampler on 20.09.17.
 */

public class GameUIScreen {

    Stage stage;
    Skin skin;
    Table table;

    public GameUIScreen(){}

    public GameUIScreen( GameScreen screen) {

        this.stage = screen.game.stage;
        this.skin = screen.game.skin;

        table = new Table();
        // Used for debugging
        table.setDebug(true);
    }
}
