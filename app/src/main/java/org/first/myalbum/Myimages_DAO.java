package org.first.myalbum;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Myimages_DAO {
    @Insert
    void insert(Myimages myimages);
    @Update
    void update(Myimages myimages);
    @Delete
    void delete(Myimages myimages);

    @Query("SELECT * FROM my_image ORDER BY id ASC ")
    LiveData<List<Myimages>> getAllimages();
}
