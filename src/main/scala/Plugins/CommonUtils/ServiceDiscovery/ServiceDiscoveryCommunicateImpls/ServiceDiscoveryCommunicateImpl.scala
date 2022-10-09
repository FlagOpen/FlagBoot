package Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryCommunicateImpls

import Plugins.CommonUtils.ServiceDiscovery.ServiceInfoDiscoveryImpls.ServiceInfoDiscoveryImpl
import Plugins.CommonUtils.ServiceDiscovery.{ServiceCode, ServiceDiscoveryCommunicateMode, ServiceDiscoveryInfoMode}
import cats.effect.IO

abstract class ServiceDiscoveryCommunicateImpl[+A <: ServiceDiscoveryCommunicateMode, B <: ServiceDiscoveryInfoMode, IT, OT]
  (implicit infoDiscoveryImpl: ServiceInfoDiscoveryImpl[B, IT, OT]) {
    def putInfo(serviceCode : ServiceCode, info : IT) : IO[Unit]
    def getInfo(serviceCode : ServiceCode) : IO[IT]
    def pullInfo() : IO[Unit]
}
