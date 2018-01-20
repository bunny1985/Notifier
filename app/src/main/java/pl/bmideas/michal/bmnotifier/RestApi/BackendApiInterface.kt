package pl.bmideas.michal.bmnotifier.RestApi

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import pl.bmideas.michal.bmnotifier.RestApi.Models.FirebaseTokenModel
import pl.bmideas.michal.bmnotifier.RestApi.Models.LoginModel
import pl.bmideas.michal.bmnotifier.RestApi.Models.NotificationModel
import pl.bmideas.michal.bmnotifier.RestApi.Models.PongModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.net.CookieManager
import java.net.CookiePolicy


/**
 * Created by michal on 12/23/17.
 */
interface BackendApiInterface {
    @POST("api/Account/Login")
    fun Login(@Body user: LoginModel): Call<Void>

    @POST("api/Notification/IsMySocketOk")
    fun Ping(): Call<PongModel>
    @POST("api/Notification/SetClipboard")
    fun SetClipBoard(@Body notification: NotificationModel): Call<Void>

    @POST("api/Notification")
    fun SaveNotification(@Body notification: NotificationModel): Call<Void>
    @POST("api/fireBase/MyToken")
    fun SaveMyToken(@Body token: FirebaseTokenModel): Call<Void>

}

