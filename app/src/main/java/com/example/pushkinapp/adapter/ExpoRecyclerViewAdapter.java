package com.example.pushkinapp.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pushkinapp.R;
import com.example.pushkinapp.databinding.ExpoCardLayoutBinding;
import com.example.pushkinapp.model.ExpoCatalogItem;

import java.util.List;

/**
 * The type Expo recycler view adapter.
 */
public class ExpoRecyclerViewAdapter extends RecyclerView.Adapter<ExpoRecyclerViewAdapter.ViewHolder> {
    private List<ExpoCatalogItem> list;
    private OnItemClickListener listener;

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
     * Instantiates a new Expo recycler view adapter.
     *
     * @param list the list
     */
    public ExpoRecyclerViewAdapter(List<ExpoCatalogItem> list) {
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
     * Get item by id expo catalog item.
     *
     * @param id the id
     * @return the expo catalog item
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ExpoCatalogItem getItemById(int id){
        if (!list.stream().filter(c -> c.getId().equals(id)).findAny().isPresent()) {
            return null;
        }
        else {
            return list.stream().filter(c -> c.getId().equals(id)).findAny().orElse(getItemAt(1));
        }
    }

    /**
     * Gets item at.
     *
     * @param position the position
     * @return the item at
     */
    public ExpoCatalogItem getItemAt(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ExpoCardLayoutBinding expoCardLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.expo_card_layout, parent, false);
        return new ViewHolder(expoCardLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.expoCardTitle.setText(list.get(position).getTitle());
        holder.binding.expoCardImage.setImageResource(list.get(position).getImgid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ExpoCardLayoutBinding binding;

        /**
         * Instantiates a new View holder.
         *
         * @param binding the binding
         */
        public ViewHolder(@NonNull ExpoCardLayoutBinding binding) {
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