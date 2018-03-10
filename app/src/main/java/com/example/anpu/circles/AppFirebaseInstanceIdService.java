package com.example.anpu.circles;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by anpu on 2018/3/7.
 */

public class AppFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public final String TAG = "AppFBInstance";

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ID service started");
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d(TAG, "token" + refreshedToken);
    }

}
