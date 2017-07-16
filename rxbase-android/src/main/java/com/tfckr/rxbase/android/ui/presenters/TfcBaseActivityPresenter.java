package com.tfckr.rxbase.android.ui.presenters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.tfckr.rxbase.android.ui.activities.TfcBaseActivity;
import com.tfckr.rxbase.android.ui.data.ActivityResult;
import com.tfckr.rxbase.android.ui.views.TfcBaseActivityView;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public class TfcBaseActivityPresenter<Activity extends TfcBaseActivity, View extends TfcBaseActivityView> {

    private final BehaviorSubject<ActivityEvent> lifecycle = BehaviorSubject.create();

    private final PublishSubject<View> viewChange = PublishSubject.create();
    private final Observable<View> view = viewChange.filter(v -> v != null);
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    private final PublishSubject<ActivityResult> activityResult = PublishSubject.create();

    private final PublishSubject<Intent> intent = PublishSubject.create();

    public TfcBaseActivityPresenter() {
    }

    /**
     * Takes activity result data from the activity.
     */
    public void activityResult(final @NonNull ActivityResult activityResult) {
        this.activityResult.onNext(activityResult);
    }

    /**
     * Takes intent data from the view.
     */
    public void intent(final @NonNull Intent intent) {
        this.intent.onNext(intent);
    }

    @CallSuper
    public void onCreate(final @NonNull Context context, final @Nullable Bundle savedInstanceState) {
        lifecycle.onNext(ActivityEvent.CREATE);
    }

    @CallSuper
    public void onStart() {
        lifecycle.onNext(ActivityEvent.START);
    }

    @CallSuper
    public void onResume() {
        lifecycle.onNext(ActivityEvent.RESUME);
    }

    @CallSuper
    public void onPause() {
        lifecycle.onNext(ActivityEvent.PAUSE);
    }

    @CallSuper
    public void onStop() {
        lifecycle.onNext(ActivityEvent.STOP);
    }

    @CallSuper
    public void onDestroy() {
        lifecycle.onNext(ActivityEvent.DESTROY);
    }

    protected
    @NonNull
    Observable<ActivityResult> activityResult() {
        return activityResult;
    }

    protected
    @NonNull
    Observable<Intent> intent() {
        return intent;
    }

    /**
     * By composing this transformer with an observable you guarantee that every observable in your view model
     * will be properly completed when the view model completes.
     * <p>
     * It is required that *every* observable in a view model do `.compose(bindToLifecycle())` before calling
     * `subscribe`.
     */
    public
    @NonNull
    <T> Observable.Transformer<T, T> bindToLifecycle() {
        return source -> source.takeUntil(
                view.switchMap(v -> v.lifecycle().map(e -> Pair.create(v, e)))
                        .filter(ve -> isFinished(ve.first, ve.second))
        );
    }

    /**
     * Determines from a view and lifecycle event if the view's life is over.
     */
    private boolean isFinished(final @NonNull View view, final @NonNull ActivityEvent event) {

        if (view instanceof TfcBaseActivity) {
            return event == ActivityEvent.DESTROY && ((TfcBaseActivity) view).isFinishing();
        }

        return event == ActivityEvent.DESTROY;
    }
}
