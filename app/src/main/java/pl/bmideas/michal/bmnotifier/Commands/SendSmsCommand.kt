package pl.bmideas.michal.bmnotifier.Commands

/**
 * Created by michal on 12/29/17.
 */
data class SendSmsCommnand (val to: String, val text: String): CommandInterface{}