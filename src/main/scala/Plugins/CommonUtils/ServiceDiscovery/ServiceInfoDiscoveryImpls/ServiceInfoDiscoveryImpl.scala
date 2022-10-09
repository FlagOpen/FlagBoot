package Plugins.CommonUtils.ServiceDiscovery.ServiceInfoDiscoveryImpls

import Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryInfoMode
import cats.effect.IO

abstract class ServiceInfoDiscoveryImpl[A <: ServiceDiscoveryInfoMode, IT, OT] {
  def gatherInfo() : IO[IT]
  def encodeInfo(x : IT) : IO[OT]
  def decodeInfo(x : OT) : IO[IT]
}
