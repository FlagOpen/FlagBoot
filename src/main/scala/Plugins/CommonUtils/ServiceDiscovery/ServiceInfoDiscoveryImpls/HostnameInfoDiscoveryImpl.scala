package Plugins.CommonUtils.ServiceDiscovery.ServiceInfoDiscoveryImpls

import Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryHostnameInfoMode
import cats.effect.IO

case class HostnameInfoDiscoveryImpl() extends ServiceInfoDiscoveryImpl[ServiceDiscoveryHostnameInfoMode, String, String] {
  override def gatherInfo(): IO[String] = ???
  override def decodeInfo(x: String): IO[String] = IO (x)
  override def encodeInfo(x: String): IO[String] = IO (x)
}
object HostnameInfoDiscoveryImpl {
  implicit val hostnameInfoDiscoveryImpl : ServiceInfoDiscoveryImpl[ServiceDiscoveryHostnameInfoMode, String, String]
    = HostnameInfoDiscoveryImpl()
}
