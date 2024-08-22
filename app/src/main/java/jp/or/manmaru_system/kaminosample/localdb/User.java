package jp.or.manmaru_system.kaminosample.localdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    public User(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){return this.id;}
    public void setId(int id){this.id = id;}

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

}
