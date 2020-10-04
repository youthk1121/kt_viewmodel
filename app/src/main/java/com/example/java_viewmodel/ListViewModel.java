package com.example.java_viewmodel;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ListViewModel extends ViewModel {
    private List<ListItemValue> itemList;
    private MutableLiveData<List<ListItemValue>> listMutableLiveData = new MutableLiveData<>();

    public List<ListItemValue> getItemList() {
        return itemList;
    }

    public void setItemList(List<ListItemValue> itemList) {
        this.itemList = itemList;
        listMutableLiveData.setValue(itemList);
    }

    public boolean isEmpty() {
        return itemList == null || itemList.isEmpty();
    }

    public LiveData<List<ListItemValue>> getLiveData() {
        return listMutableLiveData;
    }

    public int getItemIndex(@NonNull String number) {
        if (itemList == null) {
            return -1;
        }
        for (int i = 0; i < itemList.size(); i++) {
            if (number.equals(itemList.get(i).getNumber())) {
                return i;
            }
        }
        return  -1;
    }

    public boolean decreaseItemCount(int index, int count) {
        if (itemList == null) {
            return false;
        }
        ListItemValue item = itemList.get(index);
        if (item.getCurrentCount() < count) {
            return false;
        }
        item.setCurrentCount(item.getCurrentCount() - count);
        return true;
    }
}
