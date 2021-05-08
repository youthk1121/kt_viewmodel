package com.example.java_viewmodel.mainlist;

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

import com.example.java_viewmodel.R;
import com.example.java_viewmodel.countlist.CountListViewModel;

/**
 * A fragment representing a list of Items.
 */
public class MainFragment extends Fragment {

    private ListViewModel listViewModel;
    private RecyclerView recyclerView;

    private EditText numberInputView;

    private OnActivityInterActonListener interActonListener;

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
        listViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ListViewModel.class);

        recyclerView = view.findViewById(R.id.list);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ItemRecyclerViewAdapter recyclerViewAdapter = new ItemRecyclerViewAdapter(getViewLifecycleOwner());
        recyclerView.setAdapter(recyclerViewAdapter);
        listViewModel.getLiveData().observe(getViewLifecycleOwner(), recyclerViewAdapter::submitList);

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
        clearButton.setOnClickListener(v -> listViewModel.populateData());

        Button goButton = view.findViewById(R.id.go_to_count_list_button);
        goButton.setOnClickListener(v -> {
            if (interActonListener != null) {
                interActonListener.onSelectGoToCountList();
            }
        });
        Log.d("Fragment View created", String.format("  - ViewModel:%s", listViewModel.toString()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listViewModel.saveList();
    }

    public interface OnActivityInterActonListener {
        void onSelectGoToCountList();
    }
}