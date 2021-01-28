package com.app.gestiuneexamene.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.gestiuneexamene.models.Examen;

import java.util.List;

@Dao
public interface ExamenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExamen(Examen examen);

    @Query("SELECT * FROM examen")
    List<Examen> getAll();

}
