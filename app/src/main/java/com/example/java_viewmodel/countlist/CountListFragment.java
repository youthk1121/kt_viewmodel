package com.example.java_viewmodel.countlist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_viewmodel.R;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CountListFragment extends Fragment {

    private CountListViewModel countListViewModel;
    private RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CountListFragment() {
    }

    public static CountListFragment newInstance() {
        CountListFragment fragment = new CountListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Fragment create View", "[call]");
        return inflater.inflate(R.layout.fragment_count_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("Fragment View created", "[call]");
        super.onViewCreated(view, savedInstanceState);
        // recycler
        countListViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory()).get(CountListViewModel.class);
        if (countListViewModel.getItemList() == null || countListViewModel.getItemList().isEmpty()) {
            countListViewModel.setItemList(getDummyList());
        }
        recyclerView = view.findViewById(R.id.count_list);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CountListRecyclerAdapter(this, countListViewModel));

        Log.d("Fragment View created", String.format("  - ViewModel:%s", countListViewModel.toString()));
    }

    private List<CountListItemValue> getDummyList() {
        CountListItemValue item1 = new CountListItemValue("name1", "1", 1);
        CountListItemValue item2 = new CountListItemValue("name2", "2", 2);
        CountListItemValue item3 = new CountListItemValue("name3", "3", 3);
        CountListItemValue item4 = new CountListItemValue("name4", "4", 4);
        CountListItemValue item5 = new CountListItemValue("name5", "5", 5);
        CountListItemValue item6 = new CountListItemValue("name6", "6", 6);
        CountListItemValue item7 = new CountListItemValue("name7", "7", 7);
        CountListItemValue item8 = new CountListItemValue("name8", "8", 8);
        CountListItemValue item9 = new CountListItemValue("name9", "9", 9);
        CountListItemValue item10 = new CountListItemValue("name10", "10", 10);
        return Arrays.asList(item1,item2,item3,item4,item5,item6,item7,item8,item9,item10);
    }
}