package pl.bmideas.michal.bmnotifier.CommandHandlers

import android.telephony.SmsManager
import org.greenrobot.eventbus.EventBus
import pl.bmideas.michal.bmnotifier.Commands.SendSmsCommnand
import pl.bmideas.michal.bmnotifier.Events.NotificationRequestedEvent

/**
 * Created by michal on 12/29/17.
 */
class SendSmsCommandHandler : CommandHandlerInterface<SendSmsCommnand>{
    override fun handle(command: SendSmsCommnand) {
        SmsManager.getDefault().sendTextMessage(command.to ,null,  command.text , null , null)
    }
}