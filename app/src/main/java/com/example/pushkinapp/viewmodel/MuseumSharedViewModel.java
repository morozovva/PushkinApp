package com.example.pushkinapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pushkinapp.bottom_sheet.FilteringBottomSheet;
import com.example.pushkinapp.model.ExpoCatalogItem;
import com.example.pushkinapp.model.MuseumCatalogItem;
import com.example.pushkinapp.repository.MuseumDataRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Museum shared view model.
 */
public class MuseumSharedViewModel extends ViewModel {

    private final MutableLiveData<MuseumCatalogItem> selected = new MutableLiveData<>();
    private MutableLiveData<List<MuseumCatalogItem>> museumMutableData = new MutableLiveData<>();
    private final MuseumDataRepository repo;

    /**
     * Instantiates a new Museum shared view model.
     */
    public MuseumSharedViewModel() {
        repo = new MuseumDataRepository();
    }

    /**
     * Sets selected.
     *
     * @param item the item
     */
    public void setSelected(MuseumCatalogItem item) {
        selected.setValue(item);
    }

    /**
     * Gets selected.
     *
     * @return the selected
     */
    public MutableLiveData<MuseumCatalogItem> getSelected() {
        return selected;
    }

    /**
     * Gets search museum mutable live data.
     *
     * @return the search museum mutable live data
     */
    public MutableLiveData<List<MuseumCatalogItem>> getSearchMuseumMutableLiveData() {
        museumMutableData.setValue(repo.museumCatalogItemList);
        return museumMutableData;
    }

    /**
     * Gets museum mutable live data.
     *
     * @return the museum mutable live data
     */
    public MutableLiveData<List<MuseumCatalogItem>> getMuseumMutableLiveData() {
        museumMutableData.setValue(getfilter().getValue());
        return museumMutableData;
    }

    /**
     * Cheap checkbox was clicked mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<List<MuseumCatalogItem>> cheapCheckboxWasClicked() {
        List<MuseumCatalogItem> filteredList = new ArrayList<>();
        List<MuseumCatalogItem> unfilteredList = repo.museumCatalogItemList;
        MutableLiveData<List<MuseumCatalogItem>> mutableList = new MutableLiveData<>();
        for (MuseumCatalogItem item : unfilteredList) {
            if (item.getPrice()<=300) {
                filteredList.add(item);
            }
        }
        mutableList.setValue(filteredList);
        return mutableList;
    }

    /**
     * Getfilter mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<List<MuseumCatalogItem>> getfilter (){
        List<MuseumCatalogItem> filteredList = new ArrayList<>();
        List<MuseumCatalogItem> unfilteredList = repo.museumCatalogItemList;
        MutableLiveData<List<MuseumCatalogItem>> mutableList = new MutableLiveData<>();

        if (((!FilteringBottomSheet.checkFirstCheckbox()) && (!FilteringBottomSheet.checkSecondCheckbox())
                && (!FilteringBottomSheet.checkThirdCheckbox())) ||
        ((FilteringBottomSheet.checkFirstCheckbox()) && (FilteringBottomSheet.checkSecondCheckbox())
                && (FilteringBottomSheet.checkThirdCheckbox()))){
            filteredList.addAll(unfilteredList);
        }
        else {
            if (FilteringBottomSheet.checkFirstCheckbox()){
                for (MuseumCatalogItem item : unfilteredList) {
                    if (item.getPrice()<=300) {
                        filteredList.add(item);
                    }
                }
            }
            if (FilteringBottomSheet.checkSecondCheckbox()){
                for (MuseumCatalogItem item : unfilteredList) {
                    if ((item.getPrice()>=300) && (item.getPrice()<=600)) {
                        filteredList.add(item);
                    }
                }
            }
            if (FilteringBottomSheet.checkThirdCheckbox()){
                for (MuseumCatalogItem item : unfilteredList) {
                    if (item.getPrice()>=600) {
                        filteredList.add(item);
                    }
                }
            }
        }
        mutableList.setValue(filteredList);
        return mutableList;
    }

    private Integer expoSelectedIndex;
    private final MutableLiveData<ExpoCatalogItem> expoSelected = new MutableLiveData<>();
    private final MutableLiveData<List<ExpoCatalogItem>> expoMutableData = new MutableLiveData<>();

    /**
     * Set expo selected index.
     *
     * @param index the index
     */
    public void setExpoSelectedIndex(Integer index){
        expoSelectedIndex = index;
    }

    /**
     * Gets expo selected index.
     *
     * @return the expo selected index
     */
    public Integer getExpoSelectedIndex() {
        return expoSelectedIndex;
    }

    /**
     * Sets expo selected.
     *
     * @param item the item
     */
    public void setExpoSelected(ExpoCatalogItem item) {
        expoSelected.setValue(item);
    }

    /**
     * Gets selected expo.
     *
     * @return the selected expo
     */
    public MutableLiveData<ExpoCatalogItem> getSelectedExpo() {
        return expoSelected;
    }

    /**
     * Gets expo mutable live data.
     *
     * @param index the index
     * @return the expo mutable live data
     */
    public MutableLiveData<List<ExpoCatalogItem>> getExpoMutableLiveData(Integer index) {
        expoMutableData.setValue(repo.expoCatalogItemList.get(index));
        return expoMutableData;
    }
}