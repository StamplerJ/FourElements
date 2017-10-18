package com.beemelonstudio.fourelements.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.beemelonstudio.fourelements.FourElements;
import com.beemelonstudio.fourelements.entities.CircleEntity;
import com.beemelonstudio.fourelements.entities.City;
import com.beemelonstudio.fourelements.entities.Drop;
import com.beemelonstudio.fourelements.entities.Element;
import com.beemelonstudio.fourelements.entities.EntityTypes;
import com.beemelonstudio.fourelements.utils.Assets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class PlayScreen extends GameScreen {

    private PlayScreenUI playScreenUI;

    private GestureDetector gestureDetector;

    public boolean running;

    private TextureAtlas backgroundTextureAtlas;
    private TextureRegion backgroundTexture;
    private Color backgroundColor;

    public Element[] elements;
    public Element currentElement;
    public static City city;
    public static List<Drop> drops;

    public static int score;
    public float tempo;

    private Random random;
    private int[] dropData;
    private int difficulty;

    private long calcSecond, now;
    private long endPauseTime;
    private long delay;
    private boolean delayLocked;

    ShapeRenderer shr;

    public PlayScreen(FourElements game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        // UI
        playScreenUI = new PlayScreenUI(this);

        // Game Elements
        running = true;

        backgroundTextureAtlas = (TextureAtlas) Assets.get("backgroundTextureAtlas");
        backgroundTexture = backgroundTextureAtlas.findRegion("sky");
        backgroundColor = Color.valueOf("#2366b4");

        // TODO: Remove debugging
        shr = new ShapeRenderer();
        shr.setAutoShapeType(true);
        shr.setColor(0,0,0,1);

        // Add GestureListener to Multiplexer and re-set it as InputProcessor
        game.inputMultiplexer.addProcessor(createGestureDetector());
        Gdx.input.setInputProcessor(game.inputMultiplexer);

        // Setup time variables
        delay = 100;
        delayLocked = false;
        calcSecond = 0;

        // Random number generator
        random = new Random();
        dropData = new int[3];
        difficulty = 1;

        // TODO: Move this somewhere else
        game.preferences.putInteger("highscore", 0);
        score = 0;
        tempo = 0f;

        // Create elements
        elements = new Element[4];
        elements[0] = new Element( EntityTypes.AIR, 1 );
        elements[1] = new Element( EntityTypes.WATER, 2 );
        elements[2] = new Element( EntityTypes.EARTH, 3 );
        elements[3] = new Element( EntityTypes.FIRE, 4 );

        // Entities
        city = new City( 0, 0, FourElements.W_WIDTH, FourElements.W_HEIGHT * 14 / 100);
        drops = new ArrayList<Drop>();
    }

    public void update(){

        // Adjust game speed according to score
        if(score > tempo * 10){
            tempo++;

            for(Drop d : drops){
                d.velocity.y += tempo;
            }
        }

        now = System.currentTimeMillis();
        if(now > calcSecond){
            score++;
            calcSecond = now + 1000;
        }

        if(!delayLocked) {
            endPauseTime = System.currentTimeMillis() + (delay * 10);
            delayLocked = true;
        }

        // This gets executed every delay
        if(endPauseTime < System.currentTimeMillis()) {

            if (drops.size() < 10) {
                generateDrops(difficulty);
            }

            delay--;

            delayLocked = false;
        }

        Iterator<Drop> iter = drops.iterator();
        while (iter.hasNext()) {
            if (iter.next().delete) {
                iter.remove();
            }
        }
    }

    private void generateDrops( int difficulty ){

        for(int diff = 0; diff < difficulty; diff++) {

            // Get random type
            EntityTypes type = EntityTypes.class.getEnumConstants()[random.nextInt(4)];

            // x coordinates random line between 1 and 4
            // y coordinates is the top quarter of the screen
            dropData[0] = random.nextInt(6)+1;
            dropData[1] = FourElements.W_HEIGHT + CircleEntity.radius;

            Drop drop = new Drop(dropData[0], dropData[1], type, tempo);

            for(Drop d : drops){

                if(Intersector.overlaps(drop.boundingCircle, d.boundingCircle)){
                    drop.boundingCircle.y += CircleEntity.radius * 4;
                    drop.velocity.y = d.velocity.y;
                    break;
                }
            }

            drops.add(drop);

        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor( backgroundColor.r, backgroundColor.g, backgroundColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        log();

        if(running) {



            // Update world
            update();

            // Acting
            city.act(delta);

            for(int i = 0; i < elements.length; i++){
                elements[i].act(delta);
            }

            for (Drop d : drops) {
                d.act(delta);
            }

            if (city.health <= 0){
                int highscore = game.preferences.getInteger("highscore", 0);
                if( score > highscore )
                    game.preferences.putInteger("highscore", score);
                game.preferences.putInteger("currentScore", score);
                game.preferences.flush();

                game.screens.push(new GameOverScreen(game));
                game.setScreen(game.screens.peek());
            }
        }

        // Drawing
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, FourElements.W_WIDTH, FourElements.W_HEIGHT);

        for(int i = 0; i < elements.length; i++){
            elements[i].draw(batch);
        }

        for (Drop d : drops) {
            d.draw(batch);
        }

        city.draw(batch);

        batch.end();


        // TODO: Remove debugging
        shr.setProjectionMatrix(camera.combined);
        shr.begin();
        //shr.rect(1,1, FourElements.W_WIDTH, FourElements.W_HEIGHT);
        shr.end();

        playScreenUI.act(delta);
        stage.act();
        stage.draw();
    }

    private GestureDetector createGestureDetector(){
        gestureDetector = new GestureDetector(new GestureDetector.GestureListener() {
            Vector3 coordinates = new Vector3(0, 0, 0);

            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {

                if(running) {

                    coordinates.x = x;
                    coordinates.y = y;
                    camera.unproject(coordinates);

                    for (int i = 0; i < elements.length; i++) {
                        if (Intersector.overlaps(elements[i].boundingCircle, new Circle(coordinates.x, coordinates.y, 1))) {
                            if (!elements[i].moving)
                                currentElement = elements[i];
                            return true;
                        }
                    }
                    currentElement = null;

                }
                return false;
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                return false;
            }

            @Override
            public boolean longPress(float x, float y) {
                return false;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {

                if(running) {

                    if (currentElement != null) {

                        float angle = -(float) Math.atan2(velocityY, velocityX);
                        float velX = MathUtils.cos(angle) * currentElement.speed;
                        float velY = MathUtils.sin(angle) * currentElement.speed;

                        currentElement.velocity = new Vector2(velX, velY);
                    }
                }

                return false;
            }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                return false;
            }

            @Override
            public boolean panStop(float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public boolean zoom(float initialDistance, float distance) {
                return false;
            }

            @Override
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                return false;
            }

            @Override
            public void pinchStop() {

            }
        });

        return gestureDetector;
    }

    //TODO: Remove debugging
    private void log(){
        //Gdx.app.log("City", "X:" + city.boundingBox.x + " Y:" + city.boundingBox.y + " W:" + city.boundingBox.width + " H:" + city.boundingBox.height);
        //Gdx.app.log("Drop", "X:" + drops.get(0).boundingCircle.x + " Y:" + drops.get(0).boundingCircle.y + " R:" + drops.get(0).boundingCircle.radius);
        //Gdx.app.log("Health", "" + city.health);
        //if(currentElement != null)
            //Gdx.app.log("Element", "" + currentElement.boundingCircle.x + " - " + currentElement.boundingCircle.y );
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
