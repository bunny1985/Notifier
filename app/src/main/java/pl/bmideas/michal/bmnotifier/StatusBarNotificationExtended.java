package pl.bmideas.michal.bmnotifier;

import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by michal on 12/21/17.
 */

public class StatusBarNotificationExtended {


    private final StatusBarNotification statusBarNotification;





    public StatusBarNotificationExtended(StatusBarNotification statusBarNotification){
        this.statusBarNotification = statusBarNotification;
    }
    public String getTitle(){
        return statusBarNotification.getNotification().extras.getString("android.title");
    }
    public String getText(){
        return statusBarNotification.getNotification().extras.getString("android.text");
    }
    public String getPackage(){
        return  statusBarNotification.getPackageName();
    }


}
