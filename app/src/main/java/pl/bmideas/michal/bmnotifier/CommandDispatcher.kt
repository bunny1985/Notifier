package pl.bmideas.michal.bmnotifier

import pl.bmideas.michal.bmnotifier.CommandHandlers.DismissNotificationHandler
import pl.bmideas.michal.bmnotifier.CommandHandlers.GetBatteryStatusCommandHandler
import pl.bmideas.michal.bmnotifier.CommandHandlers.PlaySoundCommandHanlder
import pl.bmideas.michal.bmnotifier.CommandHandlers.SendSmsCommandHandler
import pl.bmideas.michal.bmnotifier.Commands.*

/**
 * Created by michal on 12/29/17.
 */
class CommandDispatcher private constructor(){
    private object Holder { val INSTANCE = CommandDispatcher() }
    companion object {

        val instance: CommandDispatcher by lazy { Holder.INSTANCE }
    }

    fun dispath(command : CommandInterface){
        if( command is SendSmsCommnand){
            SendSmsCommandHandler().handle(command)
        }
        if( command is PlaySoundCommand){
            PlaySoundCommandHanlder().handle(command)
        }
        if( command is DismissNotification){
            DismissNotificationHandler().handle(command)
        }
        if( command is GetBatteryStatus){
            GetBatteryStatusCommandHandler().handle(command)
        }

    }




}

