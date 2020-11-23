package com.example.java_viewmodel.contents.item;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_t", indices = {@Index("number")})
public class ItemEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "number")
    public String number;
    @ColumnInfo(name = "init_count")
    public long initCount;
    @ColumnInfo(name = "current_count")
    public long currentCount;

    public ItemEntity() {
    }

    public ItemEntity(String name, String number, long initCount, long currentCount) {
        this.name = name;
        this.number = number;
        this.initCount = initCount;
        this.currentCount = currentCount;
    }

    public ItemEntity(long id, String name, String number, long initCount, long currentCount) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.initCount = initCount;
        this.currentCount = currentCount;
    }
}
