package com.example.android_project_3_a2;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class RestaurantsActivity extends AppCompatActivity {

    private SelectionViewModel viewModel;
    private boolean isWebFragmentVisible = false;
    private static final String TAG = "RestaurantsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        // Initially add the generic list fragment with "restaurants"
        if (savedInstanceState == null) {
            GenericListFragment listFragment = GenericListFragment.newInstance("restaurants");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerList, listFragment)
                    .commit();
        }

        viewModel = new ViewModelProvider(this).get(SelectionViewModel.class);

        // Observe the selected URL; when an item is clicked, this value changes
        viewModel.getSelectedUrl().observe(this, url -> {
            if (url != null && !isWebFragmentVisible) {
                showWebFragment();
            }
        });

        // Handle back presses using onBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isWebFragmentVisible) {
                    // Try to find the WebView fragment in the container
                    Fragment webFragment = getSupportFragmentManager().findFragmentById(R.id.containerWebView);
                    if (webFragment != null) {
                        // Remove the WebView fragment
                        getSupportFragmentManager().beginTransaction()
                                .remove(webFragment)
                                .commit();
                    }
                    // Hide the WebView container and clear the selection
                    findViewById(R.id.containerWebView).setVisibility(View.GONE);
                    viewModel.selectUrl(null);
                    isWebFragmentVisible = false;
                } else {
                    // No WebView fragment, so disable our callback and let the system handle the back press
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    private void showWebFragment() {
        // Get the screen width in pixels
        int screenWidth = getResources().getDisplayMetrics().widthPixels;

        // Update the list container to occupy 1/3 of the screen width
        View listContainer = findViewById(R.id.containerList);
        RelativeLayout.LayoutParams listParams = (RelativeLayout.LayoutParams) listContainer.getLayoutParams();
        listParams.width = screenWidth / 3;
        listContainer.setLayoutParams(listParams);

        // Update the web container to occupy 2/3 of the screen width
        View webContainer = findViewById(R.id.containerWebView);
        RelativeLayout.LayoutParams webParams = (RelativeLayout.LayoutParams) webContainer.getLayoutParams();
        webParams.width = (2 * screenWidth) / 3;
        webContainer.setLayoutParams(webParams);

        // Add the WebViewFragment to the web container
        WebViewFragment webFragment = new WebViewFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerWebView, webFragment);
        ft.commit();

        // Make the web container visible and mark the fragment as visible
        webContainer.setVisibility(View.VISIBLE);
        isWebFragmentVisible = true;
    }
}
