package com.app.gestiunejucatori.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.gestiunejucatori.models.Jucator;

import java.util.List;

@Dao
public interface JucatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJucator(Jucator jucator);

    @Query("SELECT * FROM jucator")
    List<Jucator> getAll();
}
