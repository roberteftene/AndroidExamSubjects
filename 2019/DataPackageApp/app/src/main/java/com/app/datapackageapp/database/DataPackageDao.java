package com.app.datapackageapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.datapackageapp.models.DataPackage;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DataPackageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPackageToDb(DataPackage dataPackage);

    @Query("SELECT * FROM package")
    List<DataPackage> getPackagesFromDb();

}
