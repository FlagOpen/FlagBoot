package Plugins.NucleicAPI.NucleicMessages

import Plugins.NucleicAPI.NucleicMessage

case class UserCheckNucleinMessage(cellphone : String, token : String) extends NucleicMessage[Option[Boolean]]
