package pl.bmideas.michal.bmnotifier.Activities

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog;
import android.app.NotificationManager
import android.content.*

import android.text.TextUtils
import android.provider.Settings;

import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*;
import android.content.ComponentName
import android.content.pm.PackageManager
import android.service.notification.StatusBarNotification
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pl.bmideas.michal.bmnotifier.BlackBulletApplication
import pl.bmideas.michal.bmnotifier.Events.NotificationRequestedEvent
import pl.bmideas.michal.bmnotifier.Events.PingRequestedEvent
import pl.bmideas.michal.bmnotifier.Events.PongRecived

import pl.bmideas.michal.bmnotifier.Helpers.PreferencesHelper
import pl.bmideas.michal.bmnotifier.Main2Activity
import pl.bmideas.michal.bmnotifier.R
import pl.bmideas.michal.bmnotifier.RestApi.ApiServiceEventsHandler
import android.content.Intent
import android.content.Intent.*


class MainActivity : AppCompatActivity() {



    private final val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private final var ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";


    private val PERMISSION_GRANTED: Int  = 0 ;

    override fun onStart() {
        super.onStart()
        val hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS ) == PackageManager.PERMISSION_GRANTED
        if(!hasPermission){
            ActivityCompat.requestPermissions(this , arrayOf( Manifest.permission.INTERNET , Manifest.permission.SEND_SMS , Manifest.permission.READ_CONTACTS ) , PERMISSION_GRANTED)
        }

        EventBus.getDefault().register(this)
        val refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MainActivity", "Refreshed token: " + refreshedToken);

    }

    private fun isRegistered():Boolean{
        val prefs = PreferencesHelper().getPreferences();
        return prefs.getBoolean(PreferencesHelper.PrefsKeys.IS_REGISTERED , false)
    }


    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onPongRecived(event: PongRecived){
        if(event.isOk){
            this.TextConnectionInfo.text = "Connection OK"
        }else{
            this.TextConnectionInfo.text = "Connection Failed"
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get intent, action and MIME type
        val intent = intent
        val action = intent.action
        val type = intent.type
        if (ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                var s  = intent.getStringExtra(EXTRA_TEXT)
                this.InputNotificationBody.setText(s)
                this.InputNotificationTitle.setText("Shared Content")
            }
        }

        if(!isNotificationServiceEnabled()) {
            var enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();
        }

        this.TextConnectionInfo.text = "Checking..."
        ApiServiceEventsHandler().CheckIfActiveSocketConnection();

        this.ButtonCheckConnectivity.setOnClickListener({v  -> run{
            this.TextConnectionInfo.text = "Checking..."
            ApiServiceEventsHandler().CheckIfActiveSocketConnection();

        }})

        //startService(intent);
        this.ButtonSendNotification.setOnClickListener({ v -> run{
            ApiServiceEventsHandler().SendNotification( this.InputNotificationTitle.text.toString() , this.InputNotificationBody.text.toString())
        } })
        this.ButtonSendClipBoard.setOnClickListener({ v -> run{
            ApiServiceEventsHandler().SetClipBoard(this.InputNotificationBody.text.toString())
        } })
//        this.ButtonStopServices.setOnClickListener({ v -> run{
//            sendNotification(this.applicationContext , "Stopping Services" , "Sending stop Signal to services")
//            var broadcastIntent = Intent(".StopFireBaseService")
//            sendBroadcast(broadcastIntent)
//            broadcastIntent  = Intent(".StopNotificationsService")
//            sendBroadcast(broadcastIntent)
//        } })
//        this.ButtonStartServices.setOnClickListener({ v -> run{
//            sendNotification(this.applicationContext , "Starting Services" , "Sending start Signal to services")
//            var broadcastIntent = Intent(".RestartFireBaseService")
//            sendBroadcast(broadcastIntent)
//            broadcastIntent  =Intent(".RestartNotificationsService")
//            sendBroadcast(broadcastIntent)
//        } })


        if(!isRegistered()) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }


    }
    fun sendNotification(context: Context, title: String, body: String){
        EventBus.getDefault().post(NotificationRequestedEvent(title , body))
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Notifications permissions required")
        alertDialogBuilder.setMessage("This application needs special priviledge to fetch your notifications. Can we turn it on?")
        alertDialogBuilder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)) })
        alertDialogBuilder.setNegativeButton("Cancel(will not work properly)",
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
