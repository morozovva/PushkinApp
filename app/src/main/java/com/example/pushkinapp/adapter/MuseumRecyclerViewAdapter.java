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

public class MuseumRecyclerViewAdapter extends RecyclerView.Adapter<MuseumRecyclerViewAdapter.ViewHolder> {
    private List<MuseumCatalogItem> list;
    private OnItemClickListener listener;
    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public MuseumRecyclerViewAdapter(List<MuseumCatalogItem> list) {
        this.list = list;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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

//    public void filter(ViewHolder holder, int position){
//        params.height = 0;
//        if (FilteringBottomSheet.checkFirstCheckbox()){
//            if (list.get(position).getPrice()>=300){
//                holder.itemView.setLayoutParams(params);
//            }
//        }
//        else{
//            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            holder.itemView.setVisibility(View.VISIBLE);
////            holder.itemView.setLayoutParams();
//        }
//        if (FilteringBottomSheet.checkSecondCheckbox()){
//            if ((list.get(position).getPrice()<=300) || (list.get(position).getPrice()>=600)){
//                holder.itemView.setLayoutParams(params);
//            }
//        }
//        else{
//            holder.itemView.setVisibility(View.VISIBLE);
////            holder.itemView.setLayoutParams();
//        }
//        if (FilteringBottomSheet.checkThirdCheckbox()){
//            if (list.get(position).getPrice()<=600){
//                holder.itemView.setLayoutParams(params);
//            }
//        }
//        else{
//            holder.itemView.setVisibility(View.VISIBLE);
////            holder.itemView.setLayoutParams();
//        }
//    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setNewList(List<MuseumCatalogItem> newList) {
        if (list != null) {
            list.clear();
            list.addAll(newList);
        } else { // first initialization.
            list = newList;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private MuseumCardLayoutBinding binding;

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