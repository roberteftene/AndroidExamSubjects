package com.app.servicecardapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.servicecardapp.models.ServiceCard;

@Database(entities = {ServiceCard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME="cards";
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

    public abstract ServiceCardDao serviceCardDao();
}
