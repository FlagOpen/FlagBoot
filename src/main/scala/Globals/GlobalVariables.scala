package Globals

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.ServiceDiscovery.ServiceCode
import Plugins.CommonUtils.TypedSystem.API.API
import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.Status.Ok
import pureconfig.ConfigSource

object GlobalVariables {
  implicit val codeMap : Map[String, String] = ConfigSource.default.at("lightAPP").at("service-code-map").loadOrThrow[Map[String, String]]

  def mainRoutes(implicit apiSender : APISender[API]) : HttpRoutes[IO] = ???
}
