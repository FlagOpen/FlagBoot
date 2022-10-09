package Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryCommunicateImpls

import Plugins.CommonUtils.ServiceDiscovery.{RedisServiceDiscoveryCommunicateMode, ServiceCode, ServiceDiscoveryInfoMode}
import Plugins.CommonUtils.ServiceDiscovery.ServiceInfoDiscoveryImpls.ServiceInfoDiscoveryImpl
import cats.effect.{ContextShift, ExitCode, IO, IOApp, Timer}
import dev.profunktor.redis4cats.Redis
import dev.profunktor.redis4cats.effect.Log.Stdout._

case class RedisServiceDiscoveryImpl[B <: ServiceDiscoveryInfoMode, IT](implicit serviceInfoDiscoveryImpl: ServiceInfoDiscoveryImpl[B, IT, String], context : ContextShift[IO], timer : Timer[IO])

  extends ServiceDiscoveryCommunicateImpl[RedisServiceDiscoveryCommunicateMode ,B, IT, String] {

  lazy private val redisConfig = Plugins.CommonUtils.Configs.RedisServerConfig.config
  private var hostMap : Map[String, List[String]] = Map()

  override def putInfo(serviceCode: ServiceCode, info: IT): IO[Unit] = Redis[IO].utf8(s"redis://${redisConfig.hostname}").use {
    redis => for {
      encoded <- serviceInfoDiscoveryImpl.encodeInfo(info)
      _ <- redis.sAdd(serviceCode.v, encoded)
    } yield ()
  }
  override def getInfo(serviceCode: ServiceCode): IO[IT] = ???

  override def pullInfo(): IO[Unit] = ???

}

object RedisServiceDiscoveryImpl extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = IO.unit.as(ExitCode.Success)
}
