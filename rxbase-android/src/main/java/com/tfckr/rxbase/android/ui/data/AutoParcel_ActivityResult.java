package com.tfckr.rxbase.android.ui.data;

import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.BitSet;

final class AutoParcel_ActivityResult extends ActivityResult {

    private final int requestCode;
    private final int resultCode;
    private final Intent intent;

    private AutoParcel_ActivityResult(
            int requestCode,
            int resultCode,
            Intent intent) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.intent = intent;
    }

    @Override
    public int requestCode() {
        return requestCode;
    }

    @Override
    public int resultCode() {
        return resultCode;
    }

    @Nullable
    @Override
    public Intent intent() {
        return intent;
    }

    @Override
    public String toString() {
        return "ActivityResult{"
                + "requestCode=" + requestCode + ", "
                + "resultCode=" + resultCode + ", "
                + "intent=" + intent
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ActivityResult) {
            ActivityResult that = (ActivityResult) o;
            return (this.requestCode == that.requestCode())
                    && (this.resultCode == that.resultCode())
                    && ((this.intent == null) ? (that.intent() == null) : this.intent.equals(that.intent()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= this.requestCode;
        h *= 1000003;
        h ^= this.resultCode;
        h *= 1000003;
        h ^= (intent == null) ? 0 : this.intent.hashCode();
        return h;
    }

    public static final android.os.Parcelable.Creator<AutoParcel_ActivityResult> CREATOR = new android.os.Parcelable.Creator<AutoParcel_ActivityResult>() {
        @Override
        public AutoParcel_ActivityResult createFromParcel(android.os.Parcel in) {
            return new AutoParcel_ActivityResult(in);
        }

        @Override
        public AutoParcel_ActivityResult[] newArray(int size) {
            return new AutoParcel_ActivityResult[size];
        }
    };

    private final static ClassLoader CL = AutoParcel_ActivityResult.class.getClassLoader();

    private AutoParcel_ActivityResult(android.os.Parcel in) {
        this((Integer) in.readValue(CL), (Integer) in.readValue(CL), (Intent) in.readValue(CL));
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(requestCode);
        dest.writeValue(resultCode);
        dest.writeValue(intent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public ActivityResult.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder extends ActivityResult.Builder {
        private final BitSet set$ = new BitSet();
        private int requestCode;
        private int resultCode;
        private Intent intent;

        Builder() {
        }

        Builder(ActivityResult source) {
            requestCode(source.requestCode());
            resultCode(source.resultCode());
            intent(source.intent());
        }

        @Override
        public ActivityResult.Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            set$.set(0);
            return this;
        }

        @Override
        public ActivityResult.Builder resultCode(int resultCode) {
            this.resultCode = resultCode;
            set$.set(1);
            return this;
        }

        @Override
        public ActivityResult.Builder intent(Intent intent) {
            this.intent = intent;
            return this;
        }

        @Override
        public ActivityResult build() {
            if (set$.cardinality() < 2) {
                String[] propertyNames = {
                        "requestCode", "resultCode",
                };
                StringBuilder missing = new StringBuilder();
                for (int i = 0; i < 2; i++) {
                    if (!set$.get(i)) {
                        missing.append(' ').append(propertyNames[i]);
                    }
                }
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            ActivityResult result = new AutoParcel_ActivityResult(
                    this.requestCode,
                    this.resultCode,
                    this.intent);
            return result;
        }
    }
}
