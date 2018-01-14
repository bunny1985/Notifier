package pl.bmideas.michal.bmnotifier.RestApi

import org.greenrobot.eventbus.EventBus
import pl.bmideas.michal.bmnotifier.Events.*
import pl.bmideas.michal.bmnotifier.Helpers.PreferencesHelper
import pl.bmideas.michal.bmnotifier.RestApi.Models.FirebaseTokenModel
import pl.bmideas.michal.bmnotifier.RestApi.Models.LoginModel
import pl.bmideas.michal.bmnotifier.RestApi.Models.NotificationModel
import kotlin.concurrent.thread


/**
 * Created by michal on 12/23/17.
 */
class ApiServiceEventsHandler{
    //val apiUrl = "http://blackbulletapp.westeurope.cloudapp.azure.com:8080"
    val apiUrl = "http://192.168.8.106:8080"
    var api = BackendApiFactory.CreateBackendApi(apiUrl)


    fun Login(email: String, password: String){
        thread(start = true) {
            val login = LoginModel(email, password)
            try {
                var result = api.Login(login).execute();
                if(!result.isSuccessful){
                    EventBus.getDefault().post(ApiLoginAttemptFailed())
                }else{
                    EventBus.getDefault().post(ApiLoginSuccesfull(email, password))
                }
            }catch (e:Exception){
                EventBus.getDefault().post(ApiLoginAttemptFailed())
            }

        }
    }
    fun SilentLoginWithStoredCredentials(){
        val email = PreferencesHelper().getPreferences().getString(PreferencesHelper.PrefsKeys.USER_MAIL, "");
        val password = PreferencesHelper().getPreferences().getString(PreferencesHelper.PrefsKeys.USER_PASS, "");
        val login = LoginModel(email, password)
        api.Login(login).execute();
    }

    fun RefltectNotification(title : String, body : String, packageName: String){
        thread(start = true) {
            val notification = NotificationModel(title, body, packageName)
            try {
                SilentLoginWithStoredCredentials()
                var result = api.SaveNotification(notification).execute()
                if(!result.isSuccessful){
                    EventBus.getDefault().post(ApiNotificationReflectionFailed())
                }else{
                    EventBus.getDefault().post(ApiNotificationToReflectAdded())
                }
            }catch (e:Exception){
                EventBus.getDefault().post(ApiNotificationReflectionFailed())
            }

        }
    }
    fun StoreToken(token: String){
        thread(start = true) {
            val tokenModel = FirebaseTokenModel(token)
            try {
                SilentLoginWithStoredCredentials()
                var result = api.SaveMyToken(tokenModel).execute()
                if(!result.isSuccessful){
                    EventBus.getDefault().post(ApiTokenStoringError())
                }else{
                    EventBus.getDefault().post(ApiTokenStored())
                }
            }catch (e:Exception){
                EventBus.getDefault().post(ApiTokenStoringError())
            }

        }
    }


}