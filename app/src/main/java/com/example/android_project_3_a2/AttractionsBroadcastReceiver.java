package com.example.android_project_3_a2;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AttractionsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "AttractionsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "Attractions Broadcast Received!");
        Toast.makeText(context, "Opening Attractions", Toast.LENGTH_SHORT).show();

        Intent newIntent = new Intent(context, AttractionsActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);


    }
}

