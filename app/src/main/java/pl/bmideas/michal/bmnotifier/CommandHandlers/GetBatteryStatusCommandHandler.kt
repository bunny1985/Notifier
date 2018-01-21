package pl.bmideas.michal.bmnotifier.CommandHandlers

import pl.bmideas.michal.bmnotifier.Commands.GetBatteryStatus
import android.content.Intent
import android.content.IntentFilter
import pl.bmideas.michal.bmnotifier.BlackBulletApplication
import android.os.BatteryManager
import android.util.Log
import pl.bmideas.michal.bmnotifier.RestApi.ApiServiceEventsHandler

import pl.bmideas.michal.bmnotifier.RestApi.Models.BatteryStatus


/**
 * Created by michal on 1/21/18.
 */
class GetBatteryStatusCommandHandler: CommandHandlerInterface<GetBatteryStatus> {
    override fun handle(command: GetBatteryStatus) {
        Log.i("BatteryStatusHandler" , "Battery status handler");
        val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val context = BlackBulletApplication.GetApplicationDefaultContext()
        val batteryStatus = context.registerReceiver(null, ifilter)
        var batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        var atteryScale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        var batteryPct: Float = batteryLevel / atteryScale.toFloat()
        val status  = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val isCharging = status === BatteryManager.BATTERY_STATUS_CHARGING || status === BatteryManager.BATTERY_STATUS_FULL
        ApiServiceEventsHandler().SendBatteryStatus(isCharging , batteryPct)
    }
}