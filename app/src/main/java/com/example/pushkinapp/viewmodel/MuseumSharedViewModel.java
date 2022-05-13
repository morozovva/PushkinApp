package com.example.pushkinapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pushkinapp.bottom_sheet.FilteringBottomSheet;
import com.example.pushkinapp.model.ExpoCatalogItem;
import com.example.pushkinapp.model.MuseumCatalogItem;
import com.example.pushkinapp.repository.MuseumDataRepository;

import java.util.ArrayList;
import java.util.List;

public class MuseumSharedViewModel extends ViewModel {

    private final MutableLiveData<MuseumCatalogItem> selected = new MutableLiveData<>();
    private MutableLiveData<List<MuseumCatalogItem>> museumMutableData = new MutableLiveData<>();
    private final MuseumDataRepository repo;

    public MuseumSharedViewModel() {
        repo = new MuseumDataRepository();
    }

    public void setSelected(MuseumCatalogItem item) {
        selected.setValue(item);
    }

    public MutableLiveData<MuseumCatalogItem> getSelected() {
        return selected;
    }

    public MutableLiveData<List<MuseumCatalogItem>> getSearchMuseumMutableLiveData() {
        museumMutableData.setValue(repo.museumCatalogItemList);
        return museumMutableData;
    }

    public MutableLiveData<List<MuseumCatalogItem>> getMuseumMutableLiveData() {
//        museumMutableData = new MutableLiveData<>();
        museumMutableData.setValue(getfilter().getValue());
        return museumMutableData;
    }

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

    public MutableLiveData<List<MuseumCatalogItem>> getfilter (){
        List<MuseumCatalogItem> filteredList = new ArrayList<>();
        List<MuseumCatalogItem> unfilteredList = repo.museumCatalogItemList;
        MutableLiveData<List<MuseumCatalogItem>> mutableList = new MutableLiveData<>();
//        unfilteredList.setValue(repo.museumCatalogItemList);

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

//    private void search(String text) {
//        ArrayList<MuseumCatalogItem> filteredlist = new ArrayList<>();
//        getSearchList = viewModel.getMuseumMutableLiveData().getValue();
//
//        for (MuseumCatalogItem item : getSearchList) {
//            if ((item.getTitle().toLowerCase().contains(text.toLowerCase()))
////                    || (item.getSecondary().toLowerCase().contains(text.toLowerCase()))
//            ) {
//                filteredlist.add(item);
//            }
//        }
//        viewModel.getMuseumMutableLiveData().observe(getViewLifecycleOwner(), museumListUpdateObserver);
//        adapter = new MuseumRecyclerViewAdapter(filteredlist);
//        adapter.setListener((v, position) -> {
//            viewModel.setSelected(adapter.getItemAt(position));
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.container, museumCardFragment)
//                    .addToBackStack(null)
//                    .commit();
//            getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
//        });
//    }



    private Integer expoSelectedIndex;
    private final MutableLiveData<ExpoCatalogItem> expoSelected = new MutableLiveData<>();
    private final MutableLiveData<List<ExpoCatalogItem>> expoMutableData = new MutableLiveData<>();

    public void setExpoSelectedIndex(Integer index){
        expoSelectedIndex = index;
    }

    public Integer getExpoSelectedIndex() {
        return expoSelectedIndex;
    }

    public void setExpoSelected(ExpoCatalogItem item) {
        expoSelected.setValue(item);
    }

    public MutableLiveData<ExpoCatalogItem> getSelectedExpo() {
        return expoSelected;
    }

    public MutableLiveData<List<ExpoCatalogItem>> getExpoMutableLiveData(Integer index) {
        expoMutableData.setValue(repo.expoCatalogItemList.get(index));
        return expoMutableData;
    }
}