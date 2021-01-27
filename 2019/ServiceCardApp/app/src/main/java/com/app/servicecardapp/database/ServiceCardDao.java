package com.app.servicecardapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.servicecardapp.models.ServiceCard;

import java.util.List;

@Dao
public interface ServiceCardDao {

    @Query("SELECT * FROM service")
    List<ServiceCard> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertService(ServiceCard serviceCard);
}
