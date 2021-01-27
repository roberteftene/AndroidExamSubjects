package com.app.curs.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.curs.models.Curs;

import java.util.List;

@Dao
public interface CursDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurs(Curs curs);

    @Query("SELECT * FROM curs")
    List<Curs> getAll();

}
