package com.app.livrare;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.livrare.DAOs.LivrareDao;
import com.app.livrare.models.Livrare;

@Database(entities = {Livrare.class},version =  1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME="livrari";
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

    public abstract LivrareDao livrareDao();
}
