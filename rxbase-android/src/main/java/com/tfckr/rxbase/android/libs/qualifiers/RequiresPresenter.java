package com.tfckr.rxbase.android.libs.qualifiers;

import com.tfckr.rxbase.android.ui.presenters.TfcBaseActivityPresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPresenter {
    Class<? extends TfcBaseActivityPresenter> value();
}
