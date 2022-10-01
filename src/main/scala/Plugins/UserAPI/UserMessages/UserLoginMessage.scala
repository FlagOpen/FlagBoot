package Plugins.UserAPI.UserMessages

import Plugins.UserAPI.UserMessage

case class UserLoginMessage(cellphone : String, password : String) extends UserMessage[String]
