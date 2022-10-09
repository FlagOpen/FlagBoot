package Plugins.CommonUtils.Configs
import pureconfig._
import pureconfig.generic.auto._

case class RedisServerConfig(hostname : String, port : Int)

object RedisServerConfig {
  lazy val config: RedisServerConfig = ConfigSource.default.at("lightAPP").at("redis-server").loadOrThrow[RedisServerConfig]
}
