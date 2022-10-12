package Plugins.CommonUtils.Configs

import pureconfig._
import pureconfig.generic.auto._

case class HttpServerConfig(hostname : String, port : Int, apiPath : String)

object HttpServerConfig {
  lazy val config : HttpServerConfig = ConfigSource.default.at("lightAPP").at("http-server").loadOrThrow[HttpServerConfig]
}
