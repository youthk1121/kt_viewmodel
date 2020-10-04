package com.example.java_viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter}
 */
public class CountListRecyclerAdapter extends RecyclerView.Adapter<CountListRecyclerAdapter.ViewHolder> {

    private LifecycleOwner viewLifecycleOwner;
    private CountListViewModel countListViewModel;


    public CountListRecyclerAdapter(LifecycleOwner viewLifecycleOwner, CountListViewModel countListViewModel) {
        this.viewLifecycleOwner = viewLifecycleOwner;
        this.countListViewModel = countListViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_count_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = countListViewModel.getItemList().get(position);
        holder.mNameView.setText(countListViewModel.getItemList().get(position).getName());
        holder.mInitCountView.setText(String.format(Locale.getDefault(), "%d", countListViewModel.getItemList().get(position).getInitCount()));
        countListViewModel.getItemList().get(position).getCurrentCountLiveData().observe(viewLifecycleOwner, holder.observer);
        holder.mPlusButton.setOnClickListener(v -> {
            if (!countListViewModel.addItemCurrentCount(position, 1)) {
                Toast.makeText(v.getContext(), "これ以上増やせません", Toast.LENGTH_SHORT).show();
            }
        });
        holder.mMinusButton.setOnClickListener(v -> {
            if (!countListViewModel.decreaseItemCurrentCount(position, 1)) {
                Toast.makeText(v.getContext(), "これ以上減らせません", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return countListViewModel.getItemList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mInitCountView;
        public final TextView mCurrentCountView;
        public final Button mPlusButton;
        public final Button mMinusButton;
        public CountListItemValue mItem;

        private Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mCurrentCountView.setText(String.format(Locale.getDefault(), "%d", aLong));
            }
        };

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.item_name);
            mInitCountView = view.findViewById(R.id.item_init_count);
            mCurrentCountView = view.findViewById(R.id.item_current_count);
            mPlusButton = view.findViewById(R.id.plus_button);
            mMinusButton = view.findViewById(R.id.minus_button);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mCurrentCountView.getText() + "'";
        }
    }
}