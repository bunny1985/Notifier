package pl.bmideas.michal.bmnotifier.Helpers

import android.app.Activity
import android.content.SharedPreferences
import pl.bmideas.michal.bmnotifier.BlackBulletApplication

/**
 * Created by michal on 12/23/17.
 */
class PreferencesHelper(){
    val myPrefsId = "pl.yum.bmideas.systemstorage"
    val preferencesMode = Activity.MODE_PRIVATE

    public object PrefsKeys {
        val IS_REGISTERED = "IS_REGISTERED"
        val USER_PASS = "USER_PASS"
        val USER_MAIL = "USER_MAIL"
    }

    fun getPreferences() :SharedPreferences{
        val context =BlackBulletApplication.GetApplicationDefaultContext()
        return context.getSharedPreferences(myPrefsId ,preferencesMode)
    }
}