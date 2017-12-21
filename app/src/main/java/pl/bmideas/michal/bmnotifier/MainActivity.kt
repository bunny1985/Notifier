package pl.bmideas.michal.bmnotifier

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.R.string.no
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.R.string.yes
import android.app.AlertDialog;
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*

import android.text.TextUtils
import android.os.Binder
import android.provider.Settings.Secure
import android.provider.Settings;
import android.support.v7.app.NotificationCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*;
import android.content.ComponentName
import android.os.IBinder
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat



private val mConnection = object : ServiceConnection {

    override fun onServiceConnected(className: ComponentName,
                                    service: IBinder) {

    }

    override fun onServiceDisconnected(arg0: ComponentName) {

    }
}

class MainActivity : AppCompatActivity() {

    private final val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private final var ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";


    private val PERMISSION_GRANTED: Int  = 0 ;

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyNotificationListener::class.java)
        bindService(intent , mConnection,Context.BIND_AUTO_CREATE)
        val hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
        if(!hasPermission){
            ActivityCompat.requestPermissions(this , arrayOf( Manifest.permission.INTERNET) , PERMISSION_GRANTED)

        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
        enableNotificationListenerAlertDialog.show();
        val intent = Intent(this, MyNotificationListener::class.java)

        //startService(intent);
        this.button.setOnClickListener({ v -> run{
            sendNotification(this.applicationContext , "TEST" , "BODY")
        } })


    }
    fun sendNotification(context: Context, title: String, body: String){
        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_icon_background)
                .setContentTitle(title)
                .setContentText(body)


        //notification.setContentIntent(pendingIntent)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification.build())
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("właczamy")
        alertDialogBuilder.setMessage("serwis notyfikacji")
        alertDialogBuilder.setPositiveButton("tAA",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)) })
        alertDialogBuilder.setNegativeButton("NIiiii",
                DialogInterface.OnClickListener { dialog, id ->
                    // If you choose to not enable the notification listener
                    // the app. will not work as expected
                })
        return alertDialogBuilder.create()
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat = Settings.Secure.getString(contentResolver, ENABLED_NOTIFICATION_LISTENERS)
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
