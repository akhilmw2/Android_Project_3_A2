package com.example.android_project_3_a2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class SelectionViewModel extends ViewModel {

    private final MutableLiveData<String> selectedUrl = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedPosition = new MutableLiveData<>(RecyclerView.NO_POSITION);

    public void selectUrl(String url) {
        selectedUrl.setValue(url);
    }

    public LiveData<String> getSelectedUrl() {
        return selectedUrl;
    }

    // Position tracking
    public void setSelectedPosition(int position) {
        selectedPosition.setValue(position);
    }
    public LiveData<Integer> getSelectedPosition() {
        return selectedPosition;
    }


}
