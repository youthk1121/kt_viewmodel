package com.example.java_viewmodel.mainlist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.java_viewmodel.contents.ItemDatabaseRepository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private final ItemDatabaseRepository itemDatabaseRepository;

    private List<ListItemValue> itemList;
    private final MutableLiveData<List<ListItemValue>> listMutableLiveData = new MutableLiveData<>();

    public ListViewModel(@NonNull Application application) {
        super(application);
        itemDatabaseRepository = new ItemDatabaseRepository(application);
        LiveData<List<ListItemValue>> initItemValues = itemDatabaseRepository.getAllItemValues();
        initItemValues.observeForever(initValueList -> {
            itemList = initValueList;
            listMutableLiveData.postValue(initValueList);
        });
    }

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

    public void populateData() {
        itemDatabaseRepository.populateItemData();
    }

    public void saveList() {
        Log.d("ListViewModel", "numbers=" + String.join(",", listMutableLiveData.getValue().stream().map(o -> String.valueOf(o.getCurrentCount())).toArray(String[]::new)));
        itemDatabaseRepository.clearInsertList(listMutableLiveData.getValue());
    }

//    public static class ListViewModelFactory implements ViewModelProvider.Factory {
//
//        @NonNull
//        private final Application application;
//
//        private final long minInitCount;
//
//        public ListViewModelFactory(@NonNull Application application, long minInitCount) {
//            this.application = application;
//            this.minInitCount = minInitCount;
//        }
//
//        @SuppressWarnings("unchecked")
//        @NonNull
//        @Override
//        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//            if (modelClass == ListViewModel.class) {
//                return (T) new ListViewModel(application, minInitCount);
//            } else {
//                return null;
//            }
//        }
//    }
}
