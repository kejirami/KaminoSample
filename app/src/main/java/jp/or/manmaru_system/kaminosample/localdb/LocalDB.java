package jp.or.manmaru_system.kaminosample.localdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Work.class,User.class}, version = 1, exportSchema = false)
public abstract class LocalDB extends RoomDatabase {
    public abstract WorkDAO getWorkDao();
    public abstract UserDAO getUserDao();

    private static volatile LocalDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static LocalDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDB.class, "LocalDB")
                            .createFromAsset("LocalDB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
