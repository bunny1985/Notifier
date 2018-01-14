package pl.bmideas.michal.bmnotifier.RestApi.Models

/**
 * Created by michal on 12/23/17.
 */
class LoginModel {
    var email: String = ""
    var password: String = ""
    var rememberMe: Boolean = true
    constructor(email :String, password:String){
        this.email = email
        this.password = password
    }
}