package com.tmikulsk1.tvinfo.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class Favorite {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor preferencesEditor;

    public Favorite(Context context){

        sharedPreferences = context.getSharedPreferences("FAVORITES", context.MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();

    }

    public void addShowFavorite(int favorite){

        if (favorite != 0){

            preferencesEditor.putInt("showFavoriteID" + Integer.toString(favorite), favorite);
            preferencesEditor.commit();

        }

    }

    public void delShowFavorite(int favorite){

        preferencesEditor.remove("showFavoriteID" + Integer.toString(favorite));
        preferencesEditor.commit();
    }

    public int getShowFavorite(int favorite){

        return sharedPreferences.getInt("showFavoriteID" + Integer.toString(favorite), 0);

    }

    public Map<String, ?> getFavorites(){

        return sharedPreferences.getAll();

    }

    public void clearAll(){

        preferencesEditor.clear();
        preferencesEditor.commit();

    }

}
