package com.beemelonstudio.fourelements.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.beemelonstudio.fourelements.FourElements;
import com.beemelonstudio.fourelements.utils.Assets;

/**
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class City {

    public EntityTypes type = EntityTypes.CITY;

    public Rectangle boundingBox;
    private Rectangle displayBox;

    private TextureAtlas textureAtlas;
    private TextureRegion cityTexture;

    public int health;

    public City(int x, int y, int width, int height) {

        boundingBox = new Rectangle(x, y, width, height);

        textureAtlas = (TextureAtlas) Assets.get("entitiesTextureAtlas");
        cityTexture = textureAtlas.findRegion("city");

        // Calculate the graphic bounds to make some space for the trees etc.
        float scale = boundingBox.getAspectRatio();
        float displayWidth = 2 * (FourElements.W_WIDTH / 3);
        float displayHeight = displayWidth / scale;
        float displayX = (FourElements.W_WIDTH - displayWidth) / 2;
        displayBox = new Rectangle(displayX, 0, displayWidth, displayHeight);

        // Reset the boundingBox height
        boundingBox.height = displayHeight;

        health = 100;
    }

    public void draw(Batch batch) {

        batch.draw(cityTexture, displayBox.x, displayBox.y, displayBox.width, displayBox.height);
    }

    public void act(float delta) {
    }
}
