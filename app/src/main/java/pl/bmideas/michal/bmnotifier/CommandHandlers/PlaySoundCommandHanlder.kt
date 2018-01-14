package pl.bmideas.michal.bmnotifier.CommandHandlers

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.util.Log
import org.greenrobot.eventbus.EventBus
import pl.bmideas.michal.bmnotifier.BlackBulletApplication
import pl.bmideas.michal.bmnotifier.Commands.PlaySoundCommand
import pl.bmideas.michal.bmnotifier.Events.NotificationRequestedEvent

/**
 * Created by michal on 12/29/17.
 */
class PlaySoundCommandHanlder : CommandHandlerInterface<PlaySoundCommand> {

    val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .build()
    var ringotoneuri =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

    override fun handle(command: PlaySoundCommand) {
        Log.d("PlaySoundCommandHanlder" , "PLaying ringtone")
        var mp = MediaPlayer.create(BlackBulletApplication.GetApplicationDefaultContext(), ringotoneuri)
        mp.setLooping(false);
        mp.start();
        EventBus.getDefault().post( NotificationRequestedEvent("Playing ringtone" , "Playing ringtone on remote reqest"))
    }
}