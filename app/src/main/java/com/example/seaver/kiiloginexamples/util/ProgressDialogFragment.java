package com.example.seaver.kiiloginexamples.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class ProgressDialogFragment extends DialogFragment {
    public static final String FRAGMENT_TAG = "progress";
    private static final String ARGS_TITLE = "title";
    private static final String ARGS_MESSAGE = "message";

    public static ProgressDialogFragment newInstance(Context context, int titleId, int messageId) {
        return newInstance(context.getString(titleId), context.getString(messageId));
    }

    public static void hide(FragmentManager manager) {
        DialogFragment dialog = (DialogFragment) manager.findFragmentByTag(ProgressDialogFragment.FRAGMENT_TAG);
        if (dialog == null) { return; }
        dialog.dismiss();
    }
    public static ProgressDialogFragment newInstance(String title, String message) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        args.putString(ARGS_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get args
        Bundle args = getArguments();
        String title = args.getString(ARGS_TITLE);
        String msg = args.getString(ARGS_MESSAGE);

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle(title);
        progress.setMessage(msg);

        return progress;
    }
}
