package pl.bmideas.michal.bmnotifier

/**
 * Created by michal on 12/22/17.
 */

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.gson.Gson
import pl.bmideas.michal.bmnotifier.RestApi.ApiServiceEventsHandler


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }



    private fun sendRegistrationToServer(token: String?) {

        Log.i("FBIIDService" , "token")
        ApiServiceEventsHandler().StoreToken(token!!)
    }

    companion object {

        private val TAG = "MyFirebaseIIDService"
    }
}