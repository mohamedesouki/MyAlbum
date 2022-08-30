package org.first.myalbum;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Myimages.class , version = 1 , exportSchema = false )
public abstract class MyimagesDatabase extends RoomDatabase {
    private static MyimagesDatabase instance;
    public abstract Myimages_DAO myimages_dao();

    public static synchronized MyimagesDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                ,MyimagesDatabase.class,"my_images_database" ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
