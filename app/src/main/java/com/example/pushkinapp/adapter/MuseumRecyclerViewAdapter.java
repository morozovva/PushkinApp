package com.example.pushkinapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pushkinapp.R;
import com.example.pushkinapp.databinding.MuseumCardLayoutBinding;
import com.example.pushkinapp.model.MuseumCatalogItem;

import java.util.List;

/**
 * The type Museum recycler view adapter.
 */
public class MuseumRecyclerViewAdapter extends RecyclerView.Adapter<MuseumRecyclerViewAdapter.ViewHolder> {
    private List<MuseumCatalogItem> list;
    private OnItemClickListener listener;
    /**
     * The Params.
     */
    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    /**
     * The interface On item click listener.
     */
    public interface OnItemClickListener {
        /**
         * On click.
         *
         * @param view     the view
         * @param position the position
         */
        void onClick(View view, int position);
    }

    /**
     * Instantiates a new Museum recycler view adapter.
     *
     * @param list the list
     */
    public MuseumRecyclerViewAdapter(List<MuseumCatalogItem> list) {
        this.list = list;
    }

    /**
     * Sets listener.
     *
     * @param listener the listener
     */
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Gets item at.
     *
     * @param position the position
     * @return the item at
     */
    public MuseumCatalogItem getItemAt(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MuseumCardLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.museum_card_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.museumCardTitle.setText(list.get(position).getTitle());
        holder.binding.museumCardSecondary.setText(list.get(position).getSecondary());
        holder.binding.museumCardImage.setImageResource(list.get(position).getImgid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Sets new list.
     *
     * @param newList the new list
     */
    public void setNewList(List<MuseumCatalogItem> newList) {
        if (list != null) {
            list.clear();
            list.addAll(newList);
        } else { // first initialization.
            list = newList;
        }
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private MuseumCardLayoutBinding binding;
        /**
         * Instantiates a new View holder.
         *
         * @param binding the binding
         */
        public ViewHolder(@NonNull MuseumCardLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}