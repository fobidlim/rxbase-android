package com.tfckr.rxbase.sample.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.tfckr.rxbase.android.libs.qualifiers.RequiresPresenter;
import com.tfckr.rxbase.android.ui.activities.TfcBaseActivity;
import com.tfckr.rxbase.sample.R;
import com.tfckr.rxbase.sample.ui.presenters.RequestPresenter;

@RequiresPresenter(RequestPresenter.class)
public class RequestActivity extends TfcBaseActivity<RequestPresenter> {

    AppCompatButton ok, cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_request);
        ok = (AppCompatButton) findViewById(R.id.ok);
        cancel = (AppCompatButton) findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
