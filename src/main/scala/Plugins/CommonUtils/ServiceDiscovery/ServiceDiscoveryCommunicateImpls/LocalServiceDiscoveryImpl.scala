package Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryCommunicateImpls

import Plugins.CommonUtils.Exceptions.HostnameNotFoundException
import Plugins.CommonUtils.ServiceDiscovery.ServiceInfoDiscoveryImpls.ServiceInfoDiscoveryImpl
import Plugins.CommonUtils.ServiceDiscovery.{LocalServiceDiscoveryCommunicateMode, ServiceCode, ServiceDiscoveryHostnameInfoMode, ServiceDiscoveryInfoMode}
import cats.effect.IO

case class LocalServiceDiscoveryImpl[B <: ServiceDiscoveryInfoMode, IT]()(implicit serviceInfoDiscoveryImpl: ServiceInfoDiscoveryImpl[B, IT, IT],
                                                                        codeMap : Map[String, IT])
  extends ServiceDiscoveryCommunicateImpl[LocalServiceDiscoveryCommunicateMode, B, IT, IT] {

  override def putInfo(serviceCode: ServiceCode, info: IT): IO[Unit] = IO.unit
  override def getInfo(serviceCode: ServiceCode): IO[IT] = IO (codeMap.getOrElse(serviceCode.v, throw HostnameNotFoundException()))

  override def pullInfo(): IO[Unit] = IO.unit
}
object LocalServiceDiscoveryImpl {
  import Plugins.CommonUtils.ServiceDiscovery.ServiceInfoDiscoveryImpls.HostnameInfoDiscoveryImpl._
  import Globals.GlobalVariables.codeMap
  implicit val localServiceDiscoveryImpl
    = LocalServiceDiscoveryImpl[ServiceDiscoveryHostnameInfoMode, String]()
}
