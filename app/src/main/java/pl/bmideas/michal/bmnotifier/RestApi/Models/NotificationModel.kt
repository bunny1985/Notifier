package pl.bmideas.michal.bmnotifier.RestApi.Models

/**
 * Created by michal on 12/23/17.
 */
data class NotificationModel(
        var id: String = "",
        var title: String = "",
        var body: String = "",
        var `package`: String = ""
        ) {
}