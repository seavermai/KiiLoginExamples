package com.example.seaver.kiiloginexamples.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.seaver.kiiloginexamples.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This fragment shows Title view.
 * User can do the following
 * <ul>
 * <li>Register with username and password</li>
 * <li>Login with username and password</li>
 * </ul>
 */
public class MainMenuFragment extends Fragment {
    private static final int REQUEST_LOGIN = 1;
    private static final int REQUEST_REGISTER = 2;
    private static final int REQUEST_LOGIN_AS_PSEUDO_USER = 3;

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) { return; }
        switch (requestCode) {
            case REQUEST_LOGIN: {
                showLoginInfopage();
                return;
            }
            case REQUEST_REGISTER: {
                showToast(getString(R.string.registration_succeeded));
                showLoginInfopage();
                return;
            }
            case REQUEST_LOGIN_AS_PSEUDO_USER: {
                showToast("");
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button_login)
    void loginClicked() {
        UserLoginDialogFragment dialog = UserLoginDialogFragment.newInstance(this, REQUEST_LOGIN);
        dialog.show(getFragmentManager(), "");
    }

    @OnClick(R.id.button_register)
    void registerClicked() {
        UserRegistrationDialogFragment dialog = UserRegistrationDialogFragment.newInstance(this, REQUEST_REGISTER);
        dialog.show(getFragmentManager(), "");
    }
    @OnClick(R.id.button_login_pseudo_user)
    void loginPseudoUserClicked() {
//        UserLoginDialogFragment dialog = UserLoginDialogFragment.newInstance(this, REQUEST_LOGIN_AS_PSEUDO_USER);
//        dialog.show(getFragmentManager(), "");
    }

    private void showToast(String message) {
        Activity activity = getActivity();
        if (activity == null) { return; }

        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    private void showLoginInfopage() {
//        Activity activity = getActivity();
//        if (activity == null) { return; }
//
//        // store access token
//        KiiUser user = KiiUser.getCurrentUser();
//        String token = user.getAccessToken();
//        Pref.setStoredAccessToken(activity, token);
//
//        ViewUtil.toNextFragment(getFragmentManager(), BalanceListFragment.newInstance(), false);
    }
}
