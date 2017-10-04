package com.beemelonstudio.fourelements.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.beemelonstudio.fourelements.utils.Assets;

/**
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class City {

    public EntityTypes type = EntityTypes.CITY;

    public Rectangle boundingBox;

    private TextureAtlas textureAtlas;
    private TextureRegion cityTexture;

    public int health;

    public City(int x, int y, int width, int height) {

        boundingBox = new Rectangle(x, y, width, height);

        textureAtlas = (TextureAtlas) Assets.get("entitiesTextureAtlas");
        cityTexture = textureAtlas.findRegion("city");

        health = 100;
    }

    public void draw(Batch batch) {

        batch.draw(cityTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public void act(float delta) {
    }
}
