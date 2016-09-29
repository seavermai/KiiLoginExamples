package com.example.seaver.kiiloginexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.seaver.kiiloginexamples.kiiobject.Config;
import com.example.seaver.kiiloginexamples.login.MainMenuFragment;
import com.example.seaver.kiiloginexamples.util.ProgressDialogFragment;
import com.example.seaver.kiiloginexamples.util.ViewUtil;
import com.kii.cloud.storage.*;
import com.kii.cloud.storage.callback.KiiUserCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // initialize
            initKiiSDK();

            // check access token
            String token = Pref.getStoredAccessToken(getApplicationContext());
            if (TextUtils.isEmpty(token)) {
                showLoginPage();
                return;
            }

            // check access token
            ProgressDialogFragment progress = ProgressDialogFragment.newInstance(getString(R.string.login), getString(R.string.login));
            progress.show(getSupportFragmentManager(), ProgressDialogFragment.FRAGMENT_TAG);

            // login with token
            KiiUser.loginWithToken(new KiiUserCallBack() {
                @Override
                public void onLoginCompleted(int token, KiiUser user, Exception e) {
                    super.onLoginCompleted(token, user, e);
                    ProgressDialogFragment.hide(getSupportFragmentManager());

                    // error check
                    if (e != null) {
                        // go to normal login
                        showLoginPage();
                        return;
                    }
                    // login is succeeded then go to List Fragment
                    //showListPage();
                }
            }, token);
        } else {
            // Restore Kii SDK states
            Kii.onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * Initialize KiiSDK
     * Please change APP_ID/APP_KEY to your application
     */
    private void initKiiSDK() {

        Kii.initialize(
                Config.APP_ID,  // Put your App ID
                Config.APP_KEY, // Put your App Key
                Kii.Site.US            // Put your site as you've specified upon creating the app on the dev portal
        );
    }

    /**
     * Show login fragment
     */
    public void showLoginPage() {
        ViewUtil.toNextFragment(getSupportFragmentManager(), MainMenuFragment.newInstance(), false);
    }

}
