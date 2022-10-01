package Plugins.ClusterSystem.Extension

import scala.reflect.runtime.universe._

abstract class ClusterAPIExtended[Ret:TypeTag] extends ClusterAPI {
  override type ReturnType = Ret
  override def getReturnTypeTag: TypeTag[Ret] = typeTag[Ret]
}
