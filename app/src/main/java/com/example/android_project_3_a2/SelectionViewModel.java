package com.example.android_project_3_a2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectionViewModel extends ViewModel {

    private final MutableLiveData<String> selectedUrl = new MutableLiveData<>();

    public void selectUrl(String url) {
        selectedUrl.setValue(url);
    }

    public LiveData<String> getSelectedUrl() {
        return selectedUrl;
    }

}
