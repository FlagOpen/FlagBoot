package Plugins.TestMessages

import Plugins.ClusterSystem.Extension.ClusterAPIExtended
import Plugins.CommonUtils.ServiceDiscovery.ServiceCode
import com.fasterxml.jackson.annotation.JsonTypeInfo

import scala.reflect.runtime.universe._

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type"
)
abstract class LightMessage[Ret: TypeTag] extends ClusterAPIExtended[Ret] {
  override def targetServiceCode: ServiceCode = ServiceCode("0000")
}
