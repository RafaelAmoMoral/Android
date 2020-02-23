package com.example.clothes;

import android.Manifest;
import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionsManager extends AppCompatActivity {

    private static PermissionsManager me;
    private Activity activity;
    public static final int CODE_READ_EXTERNAL_STORAGE_PERMISSION = 123;
    public static final int REQUEST_SELECT_IMAGE = 456;

    public static PermissionsManager getInstance() {
        if (me == null) {
            me = new PermissionsManager();
        }
        return me;
    }

    public void getPermission(Activity a) {
        activity = a;
        requestPermmission();
    }

    public void requestPermmission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                CODE_READ_EXTERNAL_STORAGE_PERMISSION);
    }

}

