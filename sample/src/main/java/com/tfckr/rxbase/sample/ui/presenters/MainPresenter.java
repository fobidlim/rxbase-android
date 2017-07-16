package com.tfckr.rxbase.sample.ui.presenters;

import android.content.Intent;
import android.util.Log;

import com.tfckr.rxbase.android.ui.data.ActivityResult;
import com.tfckr.rxbase.android.ui.presenters.TfcBaseActivityPresenter;
import com.tfckr.rxbase.sample.ui.activities.MainActivity;
import com.tfckr.rxbase.sample.ui.views.MainView;

import rx.functions.Action1;
import rx.functions.Func1;

import static com.tfckr.rxbase.sample.ui.activities.MainActivity.REQUEST;

public class MainPresenter extends TfcBaseActivityPresenter<MainActivity, MainView> {

    public MainPresenter() {
        Log.d("MainPresenter", "MainPresenter: ");

        activityResult()
                .filter(new Func1<ActivityResult, Boolean>() {
                    @Override
                    public Boolean call(ActivityResult activityResult) {
                        Log.d("MainPresenter", "activityResult filter: requestCode? " + activityResult.requestCode());
                        return activityResult.requestCode() == REQUEST;
                    }
                })
                .doOnNext(new Action1<ActivityResult>() {
                    @Override
                    public void call(ActivityResult activityResult) {
                        Log.d("MainPresenter", "activityResult doOnNext: resultCode? " + activityResult.resultCode());
                    }
                })
                .map(new Func1<ActivityResult, Intent>() {
                    @Override
                    public Intent call(ActivityResult activityResult) {
                        return activityResult.intent();
                    }
                })
                .subscribe(new Action1<Intent>() {
                    @Override
                    public void call(Intent intent) {
                        Log.d("MainPresenter", "activityResult subscribe: ");
                    }
                });

    }
}
