package com.example.android_project_3_a2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class AttractionsActivity extends AppCompatActivity {

    private SelectionViewModel viewModel;
    private boolean isWebFragmentVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);



        // Add the generic list fragment with parameter "attractions" if not restored
        if (savedInstanceState == null) {
            GenericListFragment listFragment = GenericListFragment.newInstance("attractions");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerList, listFragment)
                    .commit();
        }

        // Get the ViewModel for observing the selected URL using "this" (the activity)
        viewModel = new ViewModelProvider(this).get(SelectionViewModel.class);

        // Observe selection changes to decide when to add the WebView fragment
        viewModel.getSelectedUrl().observe(this, url -> {
            if (url != null && !isWebFragmentVisible) {
                showWebFragment();
            }
        });

        // Use the onBackPressedDispatcher API to handle back presses
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // If the WebView fragment is visible, remove it
                if (isWebFragmentVisible) {
                    Fragment webFragment = getSupportFragmentManager().findFragmentById(R.id.containerWebView);
                    if (webFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .remove(webFragment)
                                .commit();
                    }
                    findViewById(R.id.containerWebView).setVisibility(View.GONE);
                    viewModel.selectUrl(null);
                    isWebFragmentVisible = false;
                    // Clear selection in the list fragment:
                    Fragment listFragment = getSupportFragmentManager().findFragmentById(R.id.containerList);
                    if (listFragment instanceof GenericListFragment) {
                        ((GenericListFragment) listFragment).clearSelection();
                    }
                } else {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_switch) {
            // Switch to RestaurantsActivity
            Intent intent = new Intent(this, RestaurantsActivity.class);
            startActivity(intent);
            finish(); // Optionally finish current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
