package com.beemelonstudio.fourelements.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.beemelonstudio.fourelements.entities.EntityTypes;

/**
 * Created by Jann_Luellmann on 21.07.2017.
 */

public abstract class CircleEntity {

    public Circle boundingCircle;
    public Vector2 velocity;
    public EntityTypes type;

    public TextureAtlas textureAtlas;
    public TextureRegion texture;

    public static int radius;
    public boolean delete;

    protected abstract void draw(Batch batch);
    protected abstract void act(float delta);
}
