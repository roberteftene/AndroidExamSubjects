package com.app.gestiunerezervari;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RezervareDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRezervare(Rezervare rezervare);

    @Query("SELECT * FROM rezervare")
    List<Rezervare> getAll();
}
