package pl.bmideas.michal.bmnotifier.BroadCastRecivers

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import pl.bmideas.michal.bmnotifier.BlackBulletApplication
import pl.bmideas.michal.bmnotifier.Services.MyFirebaseMessagingService
import pl.bmideas.michal.bmnotifier.Services.MyNotificationListenerService

/**
 * Created by michal on 12/29/17.
 */
class RestartServicesReciver : BroadcastReceiver() {
    companion object {
        val TAG = "BROADCASTRECIVER"
    }



    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            ".RestartNotificationsService" -> {
                context.startService(Intent(BlackBulletApplication.GetApplicationDefaultContext(), MyNotificationListenerService::class.java))
                Log.w(TAG , "Restarting notifications service")
            }
            ".RestartFireBaseService" -> {
                context.startService(Intent(BlackBulletApplication.GetApplicationDefaultContext(), MyFirebaseMessagingService::class.java))
                Log.w(TAG , "Restarting firebase service")
            }
            ".StopNotificationsService" -> {
                context.stopService(Intent(BlackBulletApplication.GetApplicationDefaultContext(), MyNotificationListenerService::class.java))
                Log.w(TAG , "Stoping notifications service")
            }
            ".StopFireBaseService" -> {
                context.startService(Intent(BlackBulletApplication.GetApplicationDefaultContext(), MyFirebaseMessagingService::class.java))
                Log.w(TAG , "Stoping firebase service")
            }

        }


    }

}