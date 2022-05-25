package com.example.pushkinapp.bottom_sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.pushkinapp.R;
import com.example.pushkinapp.model.MuseumCatalogItem;
import com.example.pushkinapp.view.CatalogFragment;
import com.example.pushkinapp.viewmodel.MuseumSharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collections;

/**
 * The type Sorting bottom sheet dialog.
 */
public class SortingBottomSheetDialog extends BottomSheetDialogFragment {

    private MuseumSharedViewModel viewModel = new MuseumSharedViewModel();

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable
                ViewGroup container, @Nullable Bundle savedInstanceState)
        {
            View v = inflater.inflate(R.layout.sorting_bottom_sheet_layout,
                    container, false);

            Button popular_button = v.findViewById(R.id.popular_button);
            Button alpha_button = v.findViewById(R.id.alphabet_button);
            Button closure_button = v.findViewById(R.id.closure_button);
            Button revertButton = v.findViewById(R.id.sorting_revert_button);

            revertButton.setOnClickListener(v12 -> {
                dismiss();
            });

            popular_button.setOnClickListener(v12 -> {
                Collections.shuffle(CatalogFragment.getViewModel().getMuseumMutableLiveData().getValue());
                dismiss();
            });

            alpha_button.setOnClickListener(v1 -> {
                Collections.sort(CatalogFragment.getViewModel().getMuseumMutableLiveData().getValue(),
                        new MuseumCatalogItem.byTitle());
                dismiss();
            });

            closure_button.setOnClickListener(v1 -> {
                Collections.shuffle(CatalogFragment.getViewModel().getMuseumMutableLiveData().getValue());
                dismiss();
            });
            return v;
        }
}
