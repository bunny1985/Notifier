package pl.bmideas.michal.bmnotifier

/**
 * Created by michal on 12/23/17.
 */
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.media.AudioAttributes
import android.support.v4.app.NotificationCompat
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.bmideas.michal.bmnotifier.Events.FireBaseEvent


class BlackBulletApplication : Application() {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!

    companion object {
        @JvmStatic
        public val NOTIFICATIONCHANLELL_ID = "pl.bmideas.michal.bmnotifier.notifications"
        @JvmStatic
        private var ApplicationDefaultContext : Context? = null
        @JvmStatic
        fun GetApplicationDefaultContext(): Context{
            return ApplicationDefaultContext!!
        }
    }
    val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .build()
    var ringotoneuri =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    override fun onCreate() {
        super.onCreate()
        ApplicationDefaultContext =  this.applicationContext

        createNotificationChanellForApp()
        EventBus.getDefault().register(this);
        // Required initialization logic here!
    }

     fun createNotificationChanellForApp(){
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
// The id of the channel.
        val id = NOTIFICATIONCHANLELL_ID
// The user-visible name of the channel.
        val name = NOTIFICATIONCHANLELL_ID
// The user-visible description of the channel.
        val description = "Black Bullet Notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val mChannel =NotificationChannel(id, name, importance)
            // Configure the notification channel.
            mChannel.description = description
            mChannel.enableLights(true)
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
            mChannel.lightColor = Color.RED


             mChannel.setSound(ringotoneuri , audioAttributes )
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotificationManager.createNotificationChannel(mChannel)
        } else {

        }

    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    override fun onLowMemory() {
        super.onLowMemory()
    }
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public fun handleFireBaseNotification(event : FireBaseEvent){

        val notification = NotificationCompat.Builder(applicationContext , BlackBulletApplication.NOTIFICATIONCHANLELL_ID  )
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(event.titile)
                .setSound(ringotoneuri)
                .setContentText(event.text)


        //notification.setContentIntent(pendingIntent)
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1024, notification.build())
    }
}