package com.app.livrare.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.livrare.models.Livrare;

import java.util.List;

@Dao
public interface LivrareDao {

    @Query("SELECT * FROM livrare")
    List<Livrare> getAllLivrari();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLivrare(Livrare livrare);

    @Update
    void updateLivrare(Livrare livrare);

    @Delete
    void deleteLivrare(Livrare livrare);

}
