package com.example.android_project_3_a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class RestaurantsBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "RestaurantsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d(TAG, "Restaurants Broadcast Received!");
        Toast.makeText(context, "Opening Restaurants", Toast.LENGTH_SHORT).show();

        Intent newIntent = new Intent(context, RestaurantsActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);

    }
}
