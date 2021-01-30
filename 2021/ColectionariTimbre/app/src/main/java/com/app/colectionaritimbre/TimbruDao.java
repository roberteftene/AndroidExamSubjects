package com.app.colectionaritimbre;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimbruDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTimbru(Timbru timbru);

    @Query("SELECT * FROM timbru")
    List<Timbru> getAll();

    @Query("DELETE FROM timbru")
    void deleteAll();

}
