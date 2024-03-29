package com.app.gestiuneexamene.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.gestiuneexamene.models.Examen;

@Database(entities = {Examen.class},version = 1)
public  abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME="examen";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance==null) {
            instance = Room.databaseBuilder(context,AppDatabase.class,DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ExamenDao examenDao();
}
