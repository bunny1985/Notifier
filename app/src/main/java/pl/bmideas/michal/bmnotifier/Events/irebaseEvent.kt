package pl.bmideas.michal.bmnotifier.Events

/**
 * Created by michal on 12/24/17.
 */
data class NotificationRequestedEvent(val titile :String, val text: String) {
}
class PingRequestedEvent()