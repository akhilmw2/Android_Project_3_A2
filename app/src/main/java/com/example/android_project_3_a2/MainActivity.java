package com.example.android_project_3_a2;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private AttractionsBroadcastReceiver attractionsReceiver;
    private RestaurantsBroadcastReceiver restaurantsReceiver;
    private static final String TAG = "BroadcastTestA2";

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("AppA2", "App A2 Launched");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Register separate receivers dynamically
        attractionsReceiver = new AttractionsBroadcastReceiver();
        restaurantsReceiver = new RestaurantsBroadcastReceiver();

        IntentFilter attractionsFilter = new IntentFilter("com.example.ATTRACTIONS");
        IntentFilter restaurantsFilter = new IntentFilter("com.example.RESTAURANTS");

        registerReceiver(attractionsReceiver, attractionsFilter, RECEIVER_EXPORTED);
        registerReceiver(restaurantsReceiver, restaurantsFilter, RECEIVER_EXPORTED);

        Log.d(TAG, "✅ Both Broadcast Receivers Registered");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (attractionsReceiver != null) {
            unregisterReceiver(attractionsReceiver);
            Log.d(TAG, "❌ Attractions Receiver Unregistered");
        }
        if (restaurantsReceiver != null) {
            unregisterReceiver(restaurantsReceiver);
            Log.d(TAG, "❌ Restaurants Receiver Unregistered");
        }
    }



}