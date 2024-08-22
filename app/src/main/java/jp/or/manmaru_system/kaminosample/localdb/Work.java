package jp.or.manmaru_system.kaminosample.localdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Works")
public class Work {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "startTime")
    private String startTime;

    public Work(String number,int userId,String startTime) {
        this.number = number;
        this.userId = userId;
        this.startTime = startTime;
    }

    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}

    public String getNumber(){return this.number;}
    public void setNumber(String number){this.number = number;}

    public int getUserId(){return this.userId;}
    public void setUserId(int userid){this.userId = userid;}
    public String getStartTime(){return this.startTime;}
    public void setStartTime(String startTime){this.startTime = startTime;}

}
