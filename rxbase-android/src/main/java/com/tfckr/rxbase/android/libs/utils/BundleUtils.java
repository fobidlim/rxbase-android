package com.tfckr.rxbase.android.libs.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class BundleUtils {

    private BundleUtils() {
    }

    public static
    @Nullable
    Bundle maybeGetBundle(final @Nullable Bundle state, final @NonNull String key) {
        if (state == null) {
            return null;
        }

        return state.getBundle(key);
    }
}
