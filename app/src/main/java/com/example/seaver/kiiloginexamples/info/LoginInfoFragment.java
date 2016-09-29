package com.example.seaver.kiiloginexamples.info;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.seaver.kiiloginexamples.R;
import com.kii.cloud.storage.KiiObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginInfoFragment extends Fragment {

    public static LoginInfoFragment newInstance() {
        return new LoginInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // show menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login_info, container, false);

        ButterKnife.bind(this, root);

        setListAdapter(new KiiObjectAdapter());

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // init ToolBar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setTitle(R.string.login_info);
        activity.setSupportActionBar(mToolBar);

        getItems();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        // clear token
        Pref.setStoredAccessToken(getActivity(), "");
        // next fragment
        ViewUtil.toNextFragment(getFragmentManager(), TitleFragment.newInstance(), false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        KiiObjectAdapter adapter = (KiiObjectAdapter) getListAdapter();
        KiiObject object = (KiiObject) adapter.getItem(position);

        // show dialog
        ItemEditDialogFragment dialog = ItemEditDialogFragment.newInstance(this, REQUEST_EDIT, object.toUri().toString(),
                object.getString(Field.NAME), object.getInt(Field.TYPE), object.getInt(Field.AMOUNT));
        dialog.show(getFragmentManager(), "");
    }

    @OnClick(R.id.button_add)
    void addClicked() {
        // show Dialog
        ItemEditDialogFragment dialog = ItemEditDialogFragment.newInstance(this, REQUEST_ADD, null, null, 0, 0);
        dialog.show(getFragmentManager(), "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) { return; }

        switch (requestCode) {
            case REQUEST_ADD: {
                String name = data.getStringExtra(ItemEditDialogFragment.RESULT_NAME);
                int type = data.getIntExtra(ItemEditDialogFragment.RESULT_TYPE, Field.Type.EXPENSE);
                int amount = data.getIntExtra(ItemEditDialogFragment.RESULT_AMOUNT, 0);

                createObject(name, type, amount);
                break;
            }
            case REQUEST_EDIT: {
                String action = data.getAction();
                String objectId = data.getStringExtra(ItemEditDialogFragment.RESULT_OBJECT_ID);
                String name = data.getStringExtra(ItemEditDialogFragment.RESULT_NAME);
                int type = data.getIntExtra(ItemEditDialogFragment.RESULT_TYPE, Field.Type.EXPENSE);
                int amount = data.getIntExtra(ItemEditDialogFragment.RESULT_AMOUNT, 0);

                if (ItemEditDialogFragment.ACTION_UPDATE.equals(action)) {
                    updateObjectInList(objectId, name, type, amount);
                } else {
                    deleteObject(objectId);
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
