package com.beemelonstudio.fourelements.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.HashMap;

/**
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class Assets {

    private static final AssetManager assetManager = new AssetManager();

    private static HashMap<String, AssetFile> files;

    public static void load(){

        //Enter all files from the assets folder in here
        //**Note that this is a very bad way to handle multiple files**
        files = new HashMap<String, AssetFile>();

        //TextureAtlases
        files.put("backgroundTextureAtlas", new AssetFile("sprites/environment.atlas",   TextureAtlas.class));
        files.put("entitiesTextureAtlas",   new AssetFile("sprites/entities.atlas",     TextureAtlas.class));
        files.put("uiTextureAtlas",         new AssetFile("sprites/ui.atlas",           TextureAtlas.class));

        //Skins
        files.put("defaultSkin",            new AssetFile("skins/default/uiskin.json",          Skin.class));
        files.put("pixthulhuSkin",          new AssetFile("skins/pixthulhu/pixthulhu-ui.json",  Skin.class));

        //I18Ns
        files.put("defaultI18N",            new AssetFile("i18N/prototype",             I18NBundle.class));

        //Loading files
        for(AssetFile asset : files.values()){
            assetManager.load(asset.path, asset.type);
        }

        assetManager.finishLoading();
        //while(!assetManager.update()){} TODO: Decide for one method
    }

    public static Object get(String hashmapKey){
        return assetManager.get(files.get(hashmapKey).path, files.get(hashmapKey).type);
    }

    public static void dispose(){
        assetManager.dispose();
    }
}
