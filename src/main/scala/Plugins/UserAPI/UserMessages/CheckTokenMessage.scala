package Plugins.UserAPI.UserMessages

import Plugins.UserAPI.UserMessage

case class CheckTokenMessage(cellphone : String, token : String) extends UserMessage[Boolean]
