package app.citate;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CitatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCitat(Citat citat);

    @Query("SELECT * FROM citat")
    List<Citat> getListaCitate();

    @Delete
    void deleteCitat(Citat citat);

    @Update
    void updateCitat(Citat citat);

}
