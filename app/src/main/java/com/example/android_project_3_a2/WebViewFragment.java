package com.example.android_project_3_a2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class WebViewFragment extends Fragment {

    private SelectionViewModel viewModel;
    private WebView webView;

    public WebViewFragment() {
        // Required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes
        setRetainInstance(true);
        viewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout containing a WebView
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        webView = view.findViewById(R.id.webView);
        // Ensure links are loaded within the WebView rather than in an external browser
        webView.setWebViewClient(new WebViewClient());

        // Observe changes in the selected URL from the ViewModel
        viewModel.getSelectedUrl().observe(getViewLifecycleOwner(), url -> {
            if (url != null) {
                webView.loadUrl(url);
            }
        });
    }
}
