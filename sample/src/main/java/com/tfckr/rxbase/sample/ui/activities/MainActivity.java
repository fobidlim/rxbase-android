package com.tfckr.rxbase.sample.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.tfckr.rxbase.android.libs.qualifiers.RequiresPresenter;
import com.tfckr.rxbase.android.ui.activities.TfcBaseActivity;
import com.tfckr.rxbase.sample.R;
import com.tfckr.rxbase.sample.ui.presenters.MainPresenter;
import com.tfckr.rxbase.sample.ui.views.MainView;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends TfcBaseActivity<MainPresenter> implements MainView {

    public static final int REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivityForResult(new Intent(this, RequestActivity.class), REQUEST);
    }
}
