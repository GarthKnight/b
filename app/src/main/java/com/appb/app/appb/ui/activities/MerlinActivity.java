package com.appb.app.appb.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.appb.app.appb.R;
import com.appb.app.appb.utils.ViewUtils;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class MerlinActivity extends BaseActivity {

    private static final String TAG = "MerlinActivity";

    protected Merlin merlin;
    protected Observable<Merlin.ConnectionStatus> connectionStatusObservable;
    protected Subscription rxSubscription;
    private ArrayList<Subscription> subscriptions = new ArrayList<>();

//    ActionConnectionStatus actionConnectionStatus = new ActionConnectionStatus() {
//        @Override
//        public void isConnected(boolean isConnected) {
//            if (tvConnectionState != null) {
//                ViewUtils.setVisibleOrGone(tvConnectionState, !getConnectionState());
//            }
//            onConnectionChange(getConnectionState());
//        }
//    };

    @Nullable
    private View tvConnectionState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merlin = createMerlin();
        connectionStatusObservable = merlin.getConnectionStatusObservable();
    }


    protected Merlin createMerlin() {
        return new Merlin.Builder()
                .withRxCallbacks()
                .withLogging(true)
                .build(this);
    }

    protected Subscription createRxSubscription() {
        return connectionStatusObservable.subscribe(
                new Action1<Merlin.ConnectionStatus>() {
                    @Override
                    public void call(Merlin.ConnectionStatus connectionStatus) {
                        onConnectionChange(getConnectionState());

                        ViewUtils.setVisibleOrGone(tvConnectionState, !getConnectionState());
                        if (getConnectionState()) {
                            Log.d(TAG, "rx connected");
                        } else {
                            Log.d(TAG, "rx disconnected");
                        }
                    }
                }
        );
    }

    public void onConnectionChange(boolean isConnected) {
    }

//    public void addConnectionRxSubscription(ActionConnectionStatus status) {
//        subscriptions.add(connectionStatusObservable.subscribe(status));
//    }
//
//    public void removeConnectionRxSubscription(ActionConnectionStatus status) {
//        subscriptions.remove(status);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        merlin.bind();
        tvConnectionState = findViewById(R.id.tvConnectionState);

        if (tvConnectionState != null) {
            ViewUtils.setVisibleOrGone(tvConnectionState, !getConnectionState());
        }
        rxSubscription = createRxSubscription();
    }

    @Override
    protected void onStop() {
        super.onStop();
        merlin.unbind();
        rxSubscription.unsubscribe();
        for (Subscription o : subscriptions) {
            o.unsubscribe();
        }
    }

    public boolean getConnectionState() {
        log("getConnectionState: " + MerlinsBeard.from(this).isConnected());
        return MerlinsBeard.from(this).isConnected();
    }

}