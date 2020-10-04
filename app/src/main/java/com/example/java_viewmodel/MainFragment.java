package com.example.java_viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MainFragment extends Fragment {

    private ListViewModel listViewModel;
    private RecyclerView recyclerView;

    private EditText numberInputView;

    private OnActivityInterActonListener interActonListener;

    private ViewModelProvider newInstanceViewModelProvider;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityInterActonListener) {
            interActonListener = (OnActivityInterActonListener) context;
        }
        newInstanceViewModelProvider = new ViewModelProvider(requireActivity(), new ViewModelProvider.NewInstanceFactory());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Fragment create View", "[call]");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("Fragment View created", "[call]");
        super.onViewCreated(view, savedInstanceState);
        // recycler
        listViewModel = newInstanceViewModelProvider.get(ListViewModel.class);
        if (listViewModel.isEmpty()) {
            listViewModel.setItemList(getDummyList());
        }
        recyclerView = view.findViewById(R.id.list);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ItemRecyclerViewAdapter(this, listViewModel));

        // button
        numberInputView = view.findViewById(R.id.code_input);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            if (recyclerView == null || recyclerView.getAdapter() == null || numberInputView == null) {
                return;
            }
            String number = numberInputView.getText().toString();
            int index = listViewModel.getItemIndex(number);
            if (index < 0) {
                Toast.makeText(getContext(), "該当項目がありません", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean successDecrease = listViewModel.decreaseItemCount(index, 1);
            if (!successDecrease) {
                Toast.makeText(getContext(), "これ以上減らせません", Toast.LENGTH_SHORT).show();
            }
        });

        Button clearButton = view.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(v -> {
            if (interActonListener != null) {
                listViewModel.setItemList(null);
                newInstanceViewModelProvider.get(CountListViewModel.class).setItemList(null);
                interActonListener.onSelectClear();
            }
        });

        Button goButton = view.findViewById(R.id.go_to_count_list_button);
        goButton.setOnClickListener(v -> {
            if (interActonListener != null) {
                interActonListener.onSelectGoToCountList();
            }
        });
        Log.d("Fragment View created", String.format("  - ViewModel:%s", listViewModel.toString()));
    }

    public interface OnActivityInterActonListener {
        void onSelectGoToCountList();
        void onSelectClear();
    }

    private List<ListItemValue> getDummyList() {
        ListItemValue item1 = new ListItemValue("name1", "1", 1);
        ListItemValue item2 = new ListItemValue("name2", "2", 2);
        ListItemValue item3 = new ListItemValue("name3", "3", 3);
        ListItemValue item4 = new ListItemValue("name4", "4", 4);
        ListItemValue item5 = new ListItemValue("name5", "5", 5);
        ListItemValue item6 = new ListItemValue("name6", "6", 6);
        ListItemValue item7 = new ListItemValue("name7", "7", 7);
        ListItemValue item8 = new ListItemValue("name8", "8", 8);
        ListItemValue item9 = new ListItemValue("name9", "9", 9);
        ListItemValue item10 = new ListItemValue("name10", "10", 10);
        return Arrays.asList(item1,item2,item3,item4,item5,item6,item7,item8,item9,item10);
    }
}