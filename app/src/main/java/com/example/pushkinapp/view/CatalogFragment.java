package com.example.pushkinapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pushkinapp.R;
import com.example.pushkinapp.adapter.MuseumRecyclerViewAdapter;
import com.example.pushkinapp.bottom_sheet.FilteringBottomSheet;
import com.example.pushkinapp.bottom_sheet.SortingBottomSheetDialog;
import com.example.pushkinapp.databinding.FragmentCatalogBinding;
import com.example.pushkinapp.model.MuseumCatalogItem;
import com.example.pushkinapp.viewmodel.MuseumSharedViewModel;

import java.util.List;

/**
 * The type Catalog fragment.
 */
public class CatalogFragment extends Fragment {
    private FragmentCatalogBinding binding;
    private final MuseumCardFragment museumCardFragment = new MuseumCardFragment();
    /**
     * The constant adapter.
     */
    public static MuseumRecyclerViewAdapter adapter;
    private static MuseumSharedViewModel viewModel;

    /**
     * Instantiates a new Catalog fragment.
     */
    public CatalogFragment() {

    }

    /**
     * Get view model museum shared view model.
     *
     * @return the museum shared view model
     */
    public static MuseumSharedViewModel getViewModel(){
        return viewModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog, container, false);
        binding.setLifecycleOwner(this);
        setSortingBottomSheet();
        setFilteringBottomSheet();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MuseumSharedViewModel.class);
        viewModel.getMuseumMutableLiveData().observe(getViewLifecycleOwner(), museumListUpdateObserver);
    }

    /**
     * Get museum list update observer observer.
     *
     * @return the observer
     */
    public Observer<List<MuseumCatalogItem>> getMuseumListUpdateObserver(){
        return museumListUpdateObserver;
    }

    /**
     * The Museum list update observer.
     */
    public Observer<List<MuseumCatalogItem>> museumListUpdateObserver = new Observer<List<MuseumCatalogItem>>() {
        @Override
        public void onChanged(List<MuseumCatalogItem> museumArrayList) {
            adapter = new MuseumRecyclerViewAdapter(museumArrayList);
            binding.museumRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.museumRecyclerView.setAdapter(adapter);
            adapter.setListener((v, position) -> {
                viewModel.setSelected(adapter.getItemAt(position));
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, museumCardFragment)
                        .addToBackStack(null)
                        .commit();
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
            });
        }
    };

    @Override
    public void onStart() {
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        super.onStart();
    }

    /**
     * Sets sorting bottom sheet.
     */
    public void setSortingBottomSheet() {
        Button sortingButton = binding.sortingButton;

        sortingButton.setOnClickListener(
                v -> {
                    SortingBottomSheetDialog sortingBottomSheetDialog = new SortingBottomSheetDialog();
                    sortingBottomSheetDialog.show(getParentFragmentManager(), "ModalBottomSheet");
                });

    }

    /**
     * Sets filtering bottom sheet.
     */
    public void setFilteringBottomSheet() {
        Button filterButton = binding.filterButton;

        filterButton.setOnClickListener(
                v -> {
                    FilteringBottomSheet filteringBottomSheet = new FilteringBottomSheet();
                    filteringBottomSheet.show(getParentFragmentManager(), "ModalBottomSheet");
                        viewModel.getfilter().observe(getViewLifecycleOwner(), museumListUpdateObserver);
                        adapter.notifyDataSetChanged();
                });
    }

    /**
     * Return adapter museum recycler view adapter.
     *
     * @return the museum recycler view adapter
     */
    public static MuseumRecyclerViewAdapter returnAdapter(){
        return adapter;
    }
}

