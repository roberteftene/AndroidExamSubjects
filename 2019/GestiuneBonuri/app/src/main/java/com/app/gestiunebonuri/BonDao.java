package com.app.gestiunebonuri;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBon(Bon bon);

    @Query("SELECT * FROM bon")
    List<Bon> getAll();

}
