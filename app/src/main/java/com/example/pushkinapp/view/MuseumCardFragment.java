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
import androidx.lifecycle.ViewModelProvider;

import com.example.pushkinapp.R;
import com.example.pushkinapp.databinding.MuseumPageBinding;
import com.example.pushkinapp.viewmodel.MuseumSharedViewModel;

/**
 * The type Museum card fragment.
 */
public class MuseumCardFragment extends Fragment {

    /**
     * The Expo list fragment.
     */
    ExpoListFragment expoListFragment = new ExpoListFragment();
    private MuseumPageBinding museumPageBinding;
    private Button backButton;

    /**
     * Instantiates a new Museum card fragment.
     */
    public MuseumCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         museumPageBinding = DataBindingUtil.inflate(inflater, R.layout.museum_page, container, false);

        backButton = museumPageBinding.backToCatalogButton;
        backButton.setOnClickListener(
                v -> {
                    getActivity().onBackPressed();
                });
        return museumPageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MuseumSharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(MuseumSharedViewModel.class);
        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
            setupExpoButton();
            viewModel.setExpoSelectedIndex(item.getId());
            System.out.println(item.getId());
            museumPageBinding.museumPic.setImageResource(item.getImgid());
            museumPageBinding.museumName.setText(item.getTitle());
            museumPageBinding.museumLogo.setImageResource(item.getLogoid());
            museumPageBinding.museumInfo.setText(item.getInfoText());
        });
    }

    private void setupExpoButton(){
        Button expoButton = museumPageBinding.expoButton;
        expoButton.setOnClickListener(
            v -> {
                getParentFragmentManager().beginTransaction()
                        .add(R.id.container, expoListFragment)
                        .addToBackStack(null)
                        .commit();
            });
    }
}
