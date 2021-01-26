package com.app.reteteculinare.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.reteteculinare.models.Reteta;

@Database(entities = {Reteta.class}, exportSchema = true, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static String DB_NAME = "retete";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract RetetaDao retetaDao();
}
