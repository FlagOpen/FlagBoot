package Plugins.UserAPI.UserMessages
import Plugins.UserAPI.UserMessage

case class UserRegisterMessage(realName : String, cellphone : String, password : String) extends UserMessage[String]
