package com.example.seaver.kiiloginexamples.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.seaver.kiiloginexamples.R;

public class ViewUtil {

    /**
     * replace with next fragment
     * @param manager is fragment manager
     * @param next is fragment you want to replace with
     * @param addBackstack true : add current fragment to backstack
     */
    public static void toNextFragment(FragmentManager manager, Fragment next, boolean addBackstack) {
        if (manager == null) { return; }
        FragmentTransaction transaction = manager.beginTransaction();
        if (addBackstack) {
            transaction.addToBackStack("");
        }
        transaction.replace(R.id.activity_main, next);
        transaction.commit();
    }
}
