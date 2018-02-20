package com.example.atul_.eatit;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by atul_ on 05/02/2018.
 */

public class Permission {


    public static void requestForPermission(Activity activity, int requestCode) {


        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&

                ContextCompat.checkSelfPermission(activity.getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);
        }
    }
}