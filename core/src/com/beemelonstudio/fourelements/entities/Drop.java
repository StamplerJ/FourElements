package com.beemelonstudio.fourelements.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.beemelonstudio.fourelements.FourElements;
import com.beemelonstudio.fourelements.screens.PlayScreen;
import com.beemelonstudio.fourelements.utils.Assets;

/**
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class Drop extends CircleEntity {

    public Drop(int line, int y, EntityTypes type, float tempo) {

        int x = line * FourElements.W_WIDTH / 7;
        CircleEntity.radius = 24;

        boundingCircle = new Circle(x, y, CircleEntity.radius);
        velocity = new Vector2(0, 0);

        this.type = type;

        velocity.y = 24 + tempo;
        delete = false;

        textureAtlas = (TextureAtlas) Assets.get("entitiesTextureAtlas");

        switch (type) {
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
            case HEALTHPACK:
                break;
            case SPEEDUP:
                break;
            case CITY:
                break;

            default:
                texture = textureAtlas.findRegion("city");
                break;
        }
    }

    @Override
    public void draw(Batch batch) {

        batch.draw(texture, boundingCircle.x - boundingCircle.radius, boundingCircle.y - boundingCircle.radius, boundingCircle.radius * 2, boundingCircle.radius * 2);
    }

    @Override
    public void act(float delta) {

        boundingCircle.y -= velocity.y * delta;

        if(Intersector.overlaps(boundingCircle, PlayScreen.city.boundingBox)) {
            delete = true;
            //PlayScreen.city.health -= 10;
        }
    }
}
