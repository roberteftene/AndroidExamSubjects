package com.app.gestiunebonuri;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Bon.class},version = 1)
public abstract class AppDatabse extends RoomDatabase {

    private static final String DB_NAME = "bon";
    private static AppDatabse instance;

    public static synchronized AppDatabse getInstance(Context context) {
        if(instance==null) {
            instance  = Room.databaseBuilder(context,AppDatabse.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract BonDao bonDao();


}
