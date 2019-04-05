package com.google.firebase.quickstart.database;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import org.litepal.LitePal;

public class Choapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);


        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }
}
