package jp.or.manmaru_system.kaminosample.localdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("DELETE FROM Users")
    void deleteAll();

    @Query("SELECT * FROM Users ORDER BY id ASC")
    List<User> getAll();
}
