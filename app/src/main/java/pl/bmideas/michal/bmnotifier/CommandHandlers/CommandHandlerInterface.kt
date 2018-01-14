package pl.bmideas.michal.bmnotifier.CommandHandlers

import pl.bmideas.michal.bmnotifier.Commands.CommandInterface

/**
 * Created by michal on 12/29/17.
 */
interface CommandHandlerInterface<T: CommandInterface> {
    fun handle(command: T)
}