package pl.bmideas.michal.bmnotifier.RestApi

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy

/**
 * Created by michal on 12/29/17.
 */
class BackendApiFactory {
    companion object {
        fun CreateBackendApi(backendUrl : String): BackendApiInterface {
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            val client = OkHttpClient.Builder().cookieJar(JavaNetCookieJar(cookieManager)).build()
            val retrofit = Retrofit.Builder()
                    .baseUrl(backendUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(BackendApiInterface::class.java)
        }


    }
}