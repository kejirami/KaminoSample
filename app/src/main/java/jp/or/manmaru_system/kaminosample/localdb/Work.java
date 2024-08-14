package jp.or.manmaru_system.kaminosample.localdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;


@Entity(tableName = "Works")
public class Work {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "memo")
    private String memo;

    public Work(String memo) {
        this.memo = memo;
    }

    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}

    public String getMemo(){return this.memo;}
    public void setMemo(String memo){this.memo = memo;}
}
