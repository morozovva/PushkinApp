package com.example.pushkinapp.bottom_sheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

import com.example.pushkinapp.R;
import com.example.pushkinapp.view.CatalogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilteringBottomSheet extends BottomSheetDialogFragment {

    public static SharedPreferences settings;
    private static CheckBox firstCheckBox;
    private static CheckBox secondCheckBox;
    private static CheckBox thirdCheckBox;
    Button revertButton;
    private static Boolean isCheckedFirst;
    private static Boolean isCheckedSecond;
    private static Boolean isCheckedThird;

//    CatalogFragment catalogFragment = new CatalogFragment();

    @Override
    public void dismissAllowingStateLoss() {
        settings.edit().clear().commit();
        super.dismissAllowingStateLoss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        isCheckedFirst = settings.getBoolean("isCheckedFirst", false);
        isCheckedSecond = settings.getBoolean("isCheckedSecond", false);
        isCheckedThird = settings.getBoolean("isCheckedThird", false);
    }
    
    //проверка выставленных настроек пользователя
    public static boolean checkFirstCheckbox(){
        if (firstCheckBox == null) {
            return false;
        }
        return firstCheckBox.isChecked();
    }

    public static boolean checkSecondCheckbox(){
        if (secondCheckBox == null) {
            return false;
        }
        return secondCheckBox.isChecked();
    }

    public static boolean checkThirdCheckbox(){
        if (thirdCheckBox == null) {
            return false;
        }
        return thirdCheckBox.isChecked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.filter_bottom_sheet_layout,
                container, false);
        firstCheckBox = v.findViewById(R.id.first_checkbox);
        secondCheckBox = v.findViewById(R.id.second_checkbox);
        thirdCheckBox = v.findViewById(R.id.third_checkbox);
        revertButton = v.findViewById(R.id.revert_button);

        firstCheckBox.setChecked(isCheckedFirst); //первое условие фильтрации
        secondCheckBox.setChecked(isCheckedSecond); //второе условие фильтрации
        thirdCheckBox.setChecked(isCheckedThird); //третье условие фильтрации

        firstCheckBox.setOnClickListener(
                (buttonView) -> {
                    settings.edit().putBoolean("isCheckedFirst", !isCheckedFirst).commit();
//                    CatalogFragment.adapter.
//                    if (checkFirstCheckbox()){
                        CatalogFragment.adapter.setNewList(CatalogFragment.getViewModel().getfilter().getValue());
                    CatalogFragment.returnAdapter().notifyDataSetChanged();
//                    }
//                    CatalogFragment.returnAdapter().notifyDataSetChanged();
//                    CatalogFragment.getViewModel().getMuseumMutableLiveData();
                });
        secondCheckBox.setOnClickListener(
                (buttonView) -> {
                    settings.edit().putBoolean("isCheckedSecond", !isCheckedSecond).commit();
                    CatalogFragment.adapter.setNewList(CatalogFragment.getViewModel().getfilter().getValue());
                    CatalogFragment.returnAdapter().notifyDataSetChanged();
////                    CatalogFragment.getViewModel().getfilter();
//                    CatalogFragment.adapter.setNewList(CatalogFragment.getViewModel().getfilter().getValue());
//                    CatalogFragment.returnAdapter().notifyDataSetChanged();
//                    CatalogFragment.getViewModel().getMuseumMutableLiveData();
//                    CatalogFragment.getViewModel().getMuseumMutableLiveData();
                });
        thirdCheckBox.setOnClickListener(
                (buttonView) -> {
                    settings.edit().putBoolean("isCheckedThird", !isCheckedThird).commit();
                    CatalogFragment.adapter.setNewList(CatalogFragment.getViewModel().getfilter().getValue());
                    CatalogFragment.returnAdapter().notifyDataSetChanged();
//                    CatalogFragment.returnAdapter().notifyDataSetChanged();
                });
        setUpRevertButton();
        return v;
    }



    private void setUpRevertButton(){
        revertButton.setOnClickListener(
                v -> {
                    firstCheckBox.setChecked(false);
                    secondCheckBox.setChecked(false);
                    thirdCheckBox.setChecked(false);
                    settings.edit().putBoolean("isCheckedFirst", false).commit();
                    settings.edit().putBoolean("isCheckedSecond", false).commit();
                    settings.edit().putBoolean("isCheckedThird", false).commit();
                });
    }
}
