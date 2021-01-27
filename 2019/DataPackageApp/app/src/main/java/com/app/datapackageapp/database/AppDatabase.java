package com.app.datapackageapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.datapackageapp.models.DataPackage;

@Database(entities = {DataPackage.class},version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "package";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract DataPackageDao dataPackageDao();

}
