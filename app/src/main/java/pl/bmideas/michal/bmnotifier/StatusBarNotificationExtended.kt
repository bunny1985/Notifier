package pl.bmideas.michal.bmnotifier

import android.os.Build
import android.service.notification.StatusBarNotification
import android.util.Log

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

import java.net.URI
import java.net.URISyntaxException

/**
 * Created by michal on 12/21/17.
 */

class StatusBarNotificationExtended(private val statusBarNotification: StatusBarNotification) {
    val title: String?
        get() = statusBarNotification.notification.extras.getString("android.title")
    val text: String?
        get() = statusBarNotification.notification.extras.getString("android.text")
    val packageName: String
        get() = statusBarNotification.packageName


}
