package com.example.whatsapclone;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class UserNetwork extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
