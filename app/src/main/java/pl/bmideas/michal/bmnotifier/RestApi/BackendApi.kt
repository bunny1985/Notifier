package pl.bmideas.michal.bmnotifier.RestApi

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.net.CookieManager
import java.net.CookiePolicy


/**
 * Created by michal on 12/23/17.
 */
interface BackendApi {
    @POST("api/Account/Login")
    fun Login(@Body user: LoginModel): Call<Void>

    @POST("api/Notification")
    fun SaveNotification(@Body notification: NotificationModel): Call<Void>
    @POST("api/fireBase/MyToken")
    fun SaveMyToken(@Body token: FirebaseTokenModel): Call<Void>

}

 class BackendApiHelper {
     companion object {
        fun CreateBackendApi(backendUrl : String): BackendApi{
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            val client = OkHttpClient.Builder().cookieJar(JavaNetCookieJar(cookieManager)).build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(BackendApi::class.java)
         }

         fun getApiCredentials(): LoginModel{
             return LoginModel("mb.michal.banas@gmail.com", "1qaz@WSX");
         }
     }
}