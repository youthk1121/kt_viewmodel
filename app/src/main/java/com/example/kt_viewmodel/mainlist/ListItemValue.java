package com.example.kt_viewmodel.mainlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ListItemValue {
    private final long id;
    private String name;
    private String number;
    private long initCount;
    private final MutableLiveData<Long> currentCountLiveData = new MutableLiveData<>();

    public ListItemValue(long id, String name, String number,  long initCount, long currentCount) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.initCount = initCount;
        this.currentCountLiveData.setValue(currentCount);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getInitCount() {
        return initCount;
    }

    public void setInitCount(long initCount) {
        this.initCount = initCount;
    }

    public long getCurrentCount() {
        Long wrapper = currentCountLiveData.getValue();
        if (wrapper == null) {
            return 0;
        }
        return wrapper;
    }

    public void setCurrentCount(long currentCountLiveData) {
        this.currentCountLiveData.setValue(currentCountLiveData);
    }

    public LiveData<Long> getCurrentCountLiveData() {
        return currentCountLiveData;
    }
}
