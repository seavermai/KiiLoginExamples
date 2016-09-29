package com.example.seaver.kiiloginexamples.login;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seaver.kiiloginexamples.R;
import com.example.seaver.kiiloginexamples.util.ProgressDialogFragment;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This dialog shows user registration form
 */
public class UserRegistrationDialogFragment extends DialogFragment {
    private static final String TAG = "RegistrationDialog";

    private static final String MESSAGE_INVALID_USERNAME = "Invalid Username";
    private static final String MESSAGE_INVALID_PASSWORD = "Invalid Password";
    private static final String MESSAGE_REGISTRATION_FAILED = "Registration is failed.";

    @Bind(R.id.text_message)
    TextView mMessageText;

    @Bind(R.id.edit_username)
    EditText mUsernameEdit;

    @Bind(R.id.edit_password)
    EditText mPasswordEdit;

    @Bind(R.id.button_submit)
    Button mSubmitButton;

    public static UserRegistrationDialogFragment newInstance(Fragment target, int requestCode) {
        UserRegistrationDialogFragment fragment = new UserRegistrationDialogFragment();
        fragment.setTargetFragment(target, requestCode);

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_registration_dialog, container, false);

        ButterKnife.bind(this, root);

        // set text
        mSubmitButton.setText(R.string.register);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.register_kii_cloud);
        return dialog;
    }

    @OnClick(R.id.button_submit)
    void submitClicked() {
        // gets username / password
        String username = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();

        // check
        if (!KiiUser.isValidUserName(username)) {
            showErrorMessage(MESSAGE_INVALID_USERNAME);
            return;
        }
        if (!KiiUser.isValidPassword(password)) {
            showErrorMessage(MESSAGE_INVALID_PASSWORD);
            return;
        }
        // show progress
        ProgressDialogFragment progress = ProgressDialogFragment.newInstance(getActivity(), R.string.register, R.string.register);
        progress.show(getFragmentManager(), ProgressDialogFragment.FRAGMENT_TAG);

        // call user registration API
        KiiUser user = KiiUser.builderWithName(username).build();
        user.register(new KiiUserCallBack() {
            @Override
            public void onRegisterCompleted(int token, KiiUser user, Exception e) {
                super.onRegisterCompleted(token, user, e);
                ProgressDialogFragment.hide(getFragmentManager());

                if (e != null) {
                    Log.e(TAG, "Register completed error", e);
                    showErrorMessage(MESSAGE_REGISTRATION_FAILED);
                    return;
                }

                // notify caller fragment that registration is done.
                Fragment target = getTargetFragment();
                if (target == null) {
                    dismiss();
                    return;
                }
                target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                dismiss();
            }
        }, password);
    }

    /**
     * Show error message
     * @param message is error message
     */
    private void showErrorMessage(String message) {
        if (mMessageText == null) { return; }

        mMessageText.setVisibility(View.VISIBLE);
        mMessageText.setText(message);
    }
}