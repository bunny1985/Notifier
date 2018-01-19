package pl.bmideas.michal.bmnotifier.CommandHandlers

import pl.bmideas.michal.bmnotifier.Commands.DismissNotification
import android.R.string.cancel
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.content.Context
import pl.bmideas.michal.bmnotifier.BlackBulletApplication
import android.content.Intent
import android.util.Log


/**
 * Created by michal on 1/19/18.
 */

class DismissNotificationHandler : CommandHandlerInterface<DismissNotification> {
    override fun handle(command: DismissNotification) {
        Log.i("DismissNotificationH" , "Canceling notification handler");
        val i = Intent("pl.bmideas.michal.bmnotifier.notification_service")
        i.putExtra("key", command.key)
        BlackBulletApplication.GetApplicationDefaultContext().sendBroadcast(i)


    }

}