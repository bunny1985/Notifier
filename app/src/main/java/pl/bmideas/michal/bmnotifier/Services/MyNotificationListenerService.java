package pl.bmideas.michal.bmnotifier.Services;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;

import pl.bmideas.michal.bmnotifier.RestApi.ApiServiceEventsHandler;

/**
 * Created by michal on 12/23/17.
 */

public class MyNotificationListenerService extends android.service.notification.NotificationListenerService {
    public static String TAG = "MBNotificationListemer";



    MyNotificationListenerService(){
        super();

    }


    private static final class ApplicationPackageNames {
        public static final String FACEBOOK_PACK_NAME = "com.facebook.katana";
        public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";
        public static final String WHATSAPP_PACK_NAME = "com.whatsapp";
        public static final String INSTAGRAM_PACK_NAME = "com.instagram.android";
    }
    @Override
    public void onNotificationPosted(final StatusBarNotification sbn){
        Log.i(TAG , "RECIVED");
        if(isValid(sbn)){
            Log.i(TAG , "Reflecting to server");
            reflectNotification(sbn);



        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG , "Restarting");
        Intent broadcastIntent = new Intent(".RestartNotificationsService");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG , "Creating");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        new ApiServiceEventsHandler().StoreToken(refreshedToken);

        CancelNotificationReciver cancelNotificationReciver = new CancelNotificationReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("pl.bmideas.michal.bmnotifier.notification_service");
        registerReceiver(cancelNotificationReciver,filter);


    }

    private void reflectNotification(StatusBarNotification sbn){

                try {
                    String id = sbn.getKey();

                    Log.i(TAG , "notification id is " + id);
                    String title = sbn.getNotification().extras.getString("android.title").toString();
                    String body = sbn.getNotification().extras.getString("android.text").toString();
                    String packageName = sbn.getPackageName().toString();
                    new ApiServiceEventsHandler().RefltectNotification(id , title, body, packageName);
                }catch(Exception e){

                }

    }



    private String getIcon(String packageName) {
        try {
            Drawable icon =  getPackageManager().getApplicationIcon(packageName);
            Bitmap map = ((BitmapDrawable)icon).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            map.compress(Bitmap.CompressFormat.PNG, 100, stream);
            String file = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            return file;
        } catch (Exception e) {
            return "";
        }
    }

    private Boolean isValid(StatusBarNotification sbn){
        if (sbn == null) return false;
        if (sbn.getPackageName() == "android") return false;
        if(sbn.getNotification().extras.getString("android.title")== null) return false;
        Notification note = sbn.getNotification();
        //if (note.sound == null && (note.vibrate == null )) return false;
        return true;
    }


    class CancelNotificationReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG , "Canceling notification");
            String key = intent.getStringExtra("key");
            MyNotificationListenerService.this.cancelNotification(key);
        }
    }


}
