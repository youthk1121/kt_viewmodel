package com.example.kt_viewmodel.countlist;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class CountListViewModel extends ViewModel {
    private List<CountListItemValue> itemList;

    public List<CountListItemValue> getItemList() {
        return itemList;
    }

    public void setItemList(List<CountListItemValue> itemList) {
        this.itemList = itemList;
    }

    public boolean addItemCurrentCount(int index, int addCount) {
        CountListItemValue item = itemList.get(index);
        if (item.getCurrentCount() + addCount > item.getInitCount()) {
            return false;
        }
        item.setCurrentCount(item.getCurrentCount() + addCount);
        return true;
    }

    public boolean decreaseItemCurrentCount(int index, int count) {
        CountListItemValue item = itemList.get(index);
        if (count > item.getCurrentCount()) {
            return false;
        }
        item.setCurrentCount(item.getCurrentCount() - count);
        return true;
    }
}
