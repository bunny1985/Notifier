package pl.bmideas.michal.bmnotifier.Events
import android.service.notification.StatusBarNotification;
/**
 * Created by michal on 12/23/17.
 */
 class ApiNotificationToReflectAdded( ) {
}
class ApiNotificationReflectionFailed() {
}
class ApiLoginAttemptFailed() {
}
data class ApiLoginSuccesfull(val email: String, val password: String) {
}
class ApiRegistrationFailed() {
}
class ApiRegistrationSuccess() {
}
class ApiTokenStored() {
}
class ApiTokenStoringError() {
}
data class PongRecived(val isOk: Boolean){

}
