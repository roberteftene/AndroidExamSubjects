package com.app.reteteculinare.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.reteteculinare.models.Reteta;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RetetaDao {

    @Query("SELECT * FROM reteta")
    List<Reteta> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReteta(Reteta reteta);

    @Update
    void updateReteta(Reteta reteta);

    @Delete
    void deleteReteta(Reteta reteta);
}
