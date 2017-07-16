package com.tfckr.rxbase.android.libs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tfckr.rxbase.android.libs.utils.BundleUtils;
import com.tfckr.rxbase.android.ui.presenters.TfcBaseActivityPresenter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class PresenterManager {

    private static final String PRESENTER_ID_KEY = "presenter_id";
    private static final String PRESENTER_STATE_KEY = "presenter_state";

    private static final PresenterManager instance = new PresenterManager();
    private Map<String, TfcBaseActivityPresenter> presenters = new HashMap<>();

    public static
    @NonNull
    PresenterManager getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T extends TfcBaseActivityPresenter> T fetch(final @NonNull Context context,
                                                        final @NonNull Class<T> presenterClass,
                                                        final @Nullable Bundle savedInstanceState) {
        final String id = fetchId(savedInstanceState);
        TfcBaseActivityPresenter activityPresenter = presenters.get(id);

        if (activityPresenter == null) {
            activityPresenter = create(context, presenterClass, savedInstanceState, id);
        }

        return (T) activityPresenter;
    }

    public void destroy(final @NonNull TfcBaseActivityPresenter activityactivityPresenter) {
        activityactivityPresenter.onDestroy();

        final Iterator<Map.Entry<String, TfcBaseActivityPresenter>> iterator = presenters.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, TfcBaseActivityPresenter> entry = iterator.next();
            if (activityactivityPresenter.equals(entry.getValue())) {
                iterator.remove();
            }
        }
    }

    public void save(final @NonNull TfcBaseActivityPresenter activityPresenter,
                     final @NonNull Bundle envelope) {
        envelope.putString(PRESENTER_ID_KEY, findIdForPresenter(activityPresenter));

        final Bundle state = new Bundle();
        envelope.putBundle(PRESENTER_STATE_KEY, state);
    }

    private <T extends TfcBaseActivityPresenter> TfcBaseActivityPresenter create(final @NonNull Context context,
                                                                                 final @NonNull Class<T> presenterClass,
                                                                                 final @Nullable Bundle savedInstanceState,
                                                                                 final @NonNull String id) {

        final TfcBaseActivityPresenter activityPresenter;

        try {
            final Constructor constructor = presenterClass.getConstructor();

            activityPresenter = (TfcBaseActivityPresenter) constructor.newInstance();
//            activityPresenter = (TfcBaseActivityPresenter) Class.forName(presenterClass.getName()).newInstance();

            // Need to catch these exceptions separately, otherwise the compiler turns them into `ReflectiveOperationException`.
            // That exception is only available in API19+
        } catch (IllegalAccessException exception) {
            throw new RuntimeException(exception);
        } catch (InvocationTargetException exception) {
            throw new RuntimeException(exception);
        } catch (InstantiationException exception) {
            throw new RuntimeException(exception);
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }

        presenters.put(id, activityPresenter);
        activityPresenter.onCreate(context, BundleUtils.maybeGetBundle(savedInstanceState, PRESENTER_STATE_KEY));

        return activityPresenter;
    }

    private String fetchId(final @Nullable Bundle savedInstanceState) {
        return savedInstanceState != null ?
                savedInstanceState.getString(PRESENTER_ID_KEY) :
                UUID.randomUUID().toString();
    }

    private String findIdForPresenter(final @NonNull TfcBaseActivityPresenter activityPresenter) {
        for (final Map.Entry<String, TfcBaseActivityPresenter> entry : presenters.entrySet()) {
            if (activityPresenter.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Cannot find presenter in map!");
    }
}
