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
import com.example.pushkinapp.adapter.ExpoRecyclerViewAdapter;
import com.example.pushkinapp.databinding.ExpoListLayoutBinding;
import com.example.pushkinapp.model.ExpoCatalogItem;
import com.example.pushkinapp.viewmodel.MuseumSharedViewModel;

import java.util.List;

public class ExpoListFragment extends Fragment {
    private ExpoListLayoutBinding expoListLayoutBinding;
    private final ExpoPageFragment expoPageFragment = new ExpoPageFragment();
    public static ExpoRecyclerViewAdapter adapter;
    private static MuseumSharedViewModel viewModel;
    private Button backButton;
    private Button scanButton;
    private QRFragment QRFragment = new QRFragment();

//    private CodeScanner mCodeScanner;

    public static MuseumSharedViewModel getViewModel(){
        return viewModel;
    }

    public ExpoListFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        expoListLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.expo_list_layout, container, false);
        expoListLayoutBinding.setLifecycleOwner(this);
//        CodeScannerView scannerView = getView().findViewById(R.id.scanner_view);
//        mCodeScanner = new CodeScanner(requireActivity(), scannerView);
        scanButton = expoListLayoutBinding.scanQr;

        scanButton.setOnClickListener(
                v -> {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container, QRFragment)
                            .addToBackStack(null)
                            .commit();
                });

        backButton = expoListLayoutBinding.backButton;
        backButton.setOnClickListener(
                v -> {
                    getActivity().onBackPressed();
                });
        return expoListLayoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MuseumSharedViewModel.class);
            viewModel.getExpoMutableLiveData(viewModel.getExpoSelectedIndex()).observe(getViewLifecycleOwner(), expoListUpdateObserver);
    }

    private Observer<List<ExpoCatalogItem>> expoListUpdateObserver = new Observer<List<ExpoCatalogItem>>() {
        @Override
        public void onChanged(List<ExpoCatalogItem> expoArrayList) {
            adapter = new ExpoRecyclerViewAdapter(expoArrayList);
            expoListLayoutBinding.expoRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            expoListLayoutBinding.expoRecyclerView.setAdapter(adapter);
            adapter.setListener((v, position) -> {
                viewModel.setExpoSelected(adapter.getItemAt(position));
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, expoPageFragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    };

    public static ExpoRecyclerViewAdapter returnAdapter(){
        return adapter;
    }
}