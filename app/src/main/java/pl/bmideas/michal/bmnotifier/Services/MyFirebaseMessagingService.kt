package pl.bmideas.michal.bmnotifier.Services

/**
 * Created by michal on 12/22/17.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.greenrobot.eventbus.EventBus
import pl.bmideas.michal.bmnotifier.Activities.MainActivity
import pl.bmideas.michal.bmnotifier.CommandDispatcher
import pl.bmideas.michal.bmnotifier.CommandHandlers.SendSmsCommandHandler
import pl.bmideas.michal.bmnotifier.Commands.PlaySoundCommand
import pl.bmideas.michal.bmnotifier.Commands.SendSmsCommnand
import pl.bmideas.michal.bmnotifier.Events.NotificationRequestedEvent
import pl.bmideas.michal.bmnotifier.R


class MyFirebaseMessagingService : FirebaseMessagingService() {



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "RECIVED FIREBASE MESSAGE")
        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            val typeId = remoteMessage.data.get("type") as String
            Log.d(TAG, typeId)
            when(typeId){
                "command.notification" -> {
                    val title = remoteMessage.data.get("contentTitle") as String
                    val body  = remoteMessage.data.get("message") as String
                    EventBus.getDefault().post(NotificationRequestedEvent(title , body));
                }
                "command.sms" -> {
                    val to = remoteMessage.data.get("to") as String
                    val body  = remoteMessage.data.get("message") as String
                    val command = SendSmsCommnand(to, body)
                    CommandDispatcher.instance.dispath(command)
                }
                "command.ringtone" -> {
                    Log.d(TAG, "PlayRingotne Command dispatching")
                    val command = PlaySoundCommand()
                    CommandDispatcher.instance.dispath(command)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //val broadcastIntent = Intent(".RestartFireBaseService")
        //sendBroadcast(broadcastIntent)
    }



    companion object {

        private val TAG = "MyFirebaseMsgService"
    }
}