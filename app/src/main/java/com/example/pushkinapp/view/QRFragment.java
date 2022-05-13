package com.example.pushkinapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.pushkinapp.R;
import com.example.pushkinapp.viewmodel.MuseumSharedViewModel;
import com.google.zxing.Result;

public class QRFragment extends Fragment {
    private CodeScanner mCodeScanner;
    public Integer resId = 0;
    MuseumSharedViewModel viewModel = new MuseumSharedViewModel();
    ExpoPageFragment expoPageFragment = new ExpoPageFragment();

    public Integer getResId(){
        return resId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.qr_layout, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resId = Integer.parseInt(result.getText());
                        System.out.println(resId);
                        mCodeScanner.releaseResources();
                        if (ExpoListFragment.adapter.getItemById(resId) == null){
                            Toast.makeText(requireActivity(), "Неверный QR-код", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            ExpoListFragment.getViewModel().setExpoSelected(ExpoListFragment.adapter.getItemById(resId));
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.container, expoPageFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
//        resId = 33;
//        if (ExpoListFragment.adapter.getItemById(resId) == null){
//            Toast.makeText(requireActivity(), "Неверный QR-код", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            ExpoListFragment.getViewModel().setExpoSelected(ExpoListFragment.adapter.getItemById(resId));
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.container, expoPageFragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
//        ExpoListFragment.getViewModel().setExpoSelected(ExpoListFragment.adapter.getItemById(resId));
//        getParentFragmentManager().beginTransaction()
//                .replace(R.id.container, expoPageFragment)
//                .addToBackStack(null)
//                .commit();
        super.onPause();
    }
}
