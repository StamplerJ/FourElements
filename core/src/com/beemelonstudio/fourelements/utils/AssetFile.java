package com.beemelonstudio.fourelements.utils;

/**
 * Created by Jann_Luellmann on 27.06.2017.
 */

public class AssetFile {

    public String path;
    public Class<?> type;

    public AssetFile(String path, Class<?> type) {
        this.path = path;
        this.type = type;
    }
}
