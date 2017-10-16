package com.beemelonstudio.fourelements.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.beemelonstudio.fourelements.FourElements;
import com.beemelonstudio.fourelements.screens.PlayScreen;
import com.beemelonstudio.fourelements.utils.Assets;
import com.beemelonstudio.fourelements.entities.EntityTypes;

/**
 * Created by Jann_Luellmann on 29.06.2017.
 */

public class Element extends CircleEntity {

    private Circle backupCircle;
    public float speed;
    public boolean moving;
    private int bounces;
    private int internalScore;

    public Element(EntityTypes type, int line) {

        this.type = type;

        int x = line * FourElements.W_WIDTH / 5;
        int y = FourElements.W_HEIGHT / 4;
        CircleEntity.radius = 32;

        boundingCircle = new Circle(x, y, CircleEntity.radius);
        backupCircle = new Circle(boundingCircle);

        velocity = new Vector2(0, 0);
        speed = 640;
        moving = false;
        bounces = 0;

        internalScore = 1;

        textureAtlas = (TextureAtlas) Assets.get("entitiesTextureAtlas");

        switch (type) {
            case HEALTHPACK: // TODO: Remove
                break;
            case WATER:
                texture = textureAtlas.findRegion("water");
                break;
            case FIRE:
                texture = textureAtlas.findRegion("fire");
                break;
            case EARTH:
                texture = textureAtlas.findRegion("earth");
                break;
            case AIR:
                texture = textureAtlas.findRegion("air");
                break;

            default:
                break;
        }

        reset();
    }

    public void draw(Batch batch) {
            batch.draw(texture, boundingCircle.x - boundingCircle.radius, boundingCircle.y - boundingCircle.radius, boundingCircle.radius * 2, boundingCircle.radius * 2);
    }

    public void act(float delta) {

        if(velocity.x != 0 || velocity.y != 0)
            moving = true;

        // Moving
        if(boundingCircle.x + CircleEntity.radius >= FourElements.W_WIDTH || boundingCircle.x - CircleEntity.radius <= 0) {
            velocity.x = -velocity.x;
            bounces++;
        }
        boundingCircle.x += velocity.x * delta;

        if(boundingCircle.y + CircleEntity.radius <= 0)
            velocity.y = -velocity.y;
        boundingCircle.y += velocity.y * delta;

        if(boundingCircle.y >= FourElements.W_HEIGHT + CircleEntity.radius)
            reset();

        if( bounces >= 3 )
            reset();

        if( velocity.x != 0 || velocity.y != 0 ) {

            // Collision detection
            for (Drop d : PlayScreen.drops) {

                if (d.type == type) {
                    if (Intersector.overlaps(d.boundingCircle, boundingCircle)) {
                        d.delete = true;
                        PlayScreen.score += internalScore;
                        internalScore *= 2;
                    }
                }
            }
        }
    }

    public void reset(){

        boundingCircle.x = backupCircle.x;
        boundingCircle.y = backupCircle.y;

        velocity.x = 0;
        velocity.y = 0;
        moving = false;

        bounces = 0;
        internalScore = 1;
    }
}
