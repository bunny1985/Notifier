package pl.bmideas.michal.bmnotifier;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONStringer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;


/**
 * Created by michal on 12/21/17.
 */

public class MyNotificationListener extends NotificationListenerService {
    public static String TAG = "NotificationListenerService";
    private WebSocketClient mWebSocketClient;
    private HashSet<String> messagesToSend = new HashSet<String>();


    public void onCreate() {

    }

    public void onNotificationRemoved(StatusBarNotification sbn){
        StatusBarNotificationExtended sbnInfo = new StatusBarNotificationExtended(sbn);
        Log.i(TAG , "REMOVED");
    }
    public void onNotificationPosted(StatusBarNotification sbn){
        StatusBarNotificationExtended sbnInfo = new StatusBarNotificationExtended(sbn);

        Log.i(TAG , "RECIVED`");
        Log.i(TAG , sbnInfo.getTitle());
        Log.i(TAG , sbnInfo.getText());
        Log.i(TAG , sbnInfo.getPackage());
        JSONStringer stringer = new JSONStringer();
        try {
            stringer.object();
            stringer.key("title");
            stringer.value(sbnInfo.getTitle());
            stringer.key("body");
            stringer.value(sbnInfo.getText());
            stringer.endObject();
        } catch (JSONException e) {

        }
        messagesToSend.add(stringer.toString());
        connectWebSocket();

        //messagesToSend.add({"title": "Dupa" , "body": "srakaandroid"})

        //sendMessage("{\"title\": \"Dupa\" , \"body\": \"srakaandroid\"}");

    }





    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.8.106:9001");
        } catch (URISyntaxException e) {

            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                for (String message: messagesToSend) {
                    this.send(message);
                }
                messagesToSend.clear();
            }

            @Override
            public void onMessage(String s) {

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
                this.connect();
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }

        };
        mWebSocketClient.connect();
    }
}
