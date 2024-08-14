package jp.or.manmaru_system.kaminosample.localdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface WorkDAO {
    @Insert
    void insert(Work work);

    @Query("DELETE FROM Works")
    void deleteAll();

    @Query("SELECT * FROM Works ORDER BY id ASC")
    List<Work> getAll();
}
