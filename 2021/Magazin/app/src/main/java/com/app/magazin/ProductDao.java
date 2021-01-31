package com.app.magazin;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(Produs produs);

    @Query("SELECT * FROM produs")
    List<Produs> getAll();

    @Query("DELETE FROM produs")
    void delete();
}
