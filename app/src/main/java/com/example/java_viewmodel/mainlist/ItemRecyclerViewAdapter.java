package com.example.java_viewmodel.mainlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_viewmodel.R;

import java.util.Locale;
import java.util.Objects;

/**
 * {@link RecyclerView.Adapter}
 */
public class ItemRecyclerViewAdapter extends ListAdapter<ListItemValue, ItemRecyclerViewAdapter.ViewHolder> {

    private final LifecycleOwner viewLifecycleOwner;

    private static DiffUtil.ItemCallback<ListItemValue> getItemCallback() {
        return new DiffUtil.ItemCallback<ListItemValue>() {
            @Override
            public boolean areItemsTheSame(@NonNull ListItemValue oldItem, @NonNull ListItemValue newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull ListItemValue oldItem, @NonNull ListItemValue newItem) {
                return Objects.equals(oldItem.getNumber(), newItem.getNumber());
            }
        };
    }

    public ItemRecyclerViewAdapter(LifecycleOwner viewLifecycleOwner) {
        super(getItemCallback());
        this.viewLifecycleOwner = viewLifecycleOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = getItem(position);
        holder.mNameView.setText(getItem(position).getName());
        getItem(position).getCurrentCountLiveData().observe(viewLifecycleOwner, holder.observer);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mCountView;
        public ListItemValue mItem;

        private final Observer<Long> observer = new Observer<Long>() {
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