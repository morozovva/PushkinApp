package com.example.pushkinapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pushkinapp.R;
import com.example.pushkinapp.adapter.MuseumRecyclerViewAdapter;
import com.example.pushkinapp.databinding.FragmentSearchBinding;
import com.example.pushkinapp.model.MuseumCatalogItem;
import com.example.pushkinapp.viewmodel.MuseumSharedViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Search fragment.
 */
public class SearchFragment extends Fragment {

    private MuseumCardFragment museumCardFragment = new MuseumCardFragment();

    /**
     * The Binding.
     */
    FragmentSearchBinding binding;
    private MuseumRecyclerViewAdapter adapter;
    private MuseumSharedViewModel viewModel = new MuseumSharedViewModel();
    private List<MuseumCatalogItem> getSearchList;

    /**
     * Instantiates a new Search fragment.
     */
    public SearchFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        binding.setLifecycleOwner(this);

        SearchView searchView = binding.searchField;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MuseumSharedViewModel.class);
    }

    private Observer<List<MuseumCatalogItem>> museumListUpdateObserver
            = new Observer<List<MuseumCatalogItem>>() {
        @Override
        public void onChanged(List<MuseumCatalogItem> museumArrayList) {
            binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.searchRecyclerView.setAdapter(adapter);
        }
    };

    @Override
    public void onStart() {
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        super.onStart();
    }

    private void search(String text) {
        ArrayList<MuseumCatalogItem> filteredlist = new ArrayList<>();
        getSearchList = viewModel.getSearchMuseumMutableLiveData().getValue();

        for (MuseumCatalogItem item : getSearchList) {
            if ((item.getTitle().toLowerCase().contains(text.toLowerCase()))) {
                filteredlist.add(item);
            }
        }
        viewModel.getSearchMuseumMutableLiveData().observe(getViewLifecycleOwner(), museumListUpdateObserver);
        adapter = new MuseumRecyclerViewAdapter(filteredlist);
        adapter.setListener((v, position) -> {
            viewModel.setSelected(adapter.getItemAt(position));
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container, museumCardFragment)
                    .addToBackStack(null)
                    .commit();
            getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        });
    }
}