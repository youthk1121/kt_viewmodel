package com.example.java_viewmodel.mainlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_viewmodel.R;

import java.util.Locale;

/**
 * {@link RecyclerView.Adapter}
 */
public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private LifecycleOwner viewLifecycleOwner;
    private ListViewModel listViewModel;

    public ItemRecyclerViewAdapter(LifecycleOwner viewLifecycleOwner, ListViewModel listViewModel) {
        this.viewLifecycleOwner = viewLifecycleOwner;
        this.listViewModel = listViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = listViewModel.getItemList().get(position);
        holder.mNameView.setText(listViewModel.getItemList().get(position).getName());
        listViewModel.getItemList().get(position).getCurrentCountLiveData().observe(viewLifecycleOwner, holder.observer);
    }

    @Override
    public int getItemCount() {
        return listViewModel.getItemList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mCountView;
        public ListItemValue mItem;

        private Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mCountView.setText(String.format(Locale.getDefault(), "%d", aLong));
            }
        };

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.item_name);
            mCountView = view.findViewById(R.id.item_count);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mCountView.getText() + "'";
        }
    }
}