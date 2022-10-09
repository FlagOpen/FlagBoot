package Globals

import Plugins.CommonUtils.ServiceDiscovery.ServiceCode
import Plugins.TestMessages.LightMessage
import pureconfig.ConfigSource

object GlobalVariables {
  val serviceCode : ServiceCode = ServiceCode("0000")
  implicit val codeMap : Map[String, String] = ConfigSource.default.at("lightAPP").at("service-code-map").loadOrThrow[Map[String, String]]

  type APIType = LightMessage[Any]
}
