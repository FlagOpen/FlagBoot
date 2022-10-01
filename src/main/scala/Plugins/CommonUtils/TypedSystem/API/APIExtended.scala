package Plugins.CommonUtils.TypedSystem.API

import scala.reflect.runtime.universe._

abstract class APIExtended[Ret:TypeTag] extends API{
  override type ReturnType = Ret
  override def getReturnTypeTag: TypeTag[Ret] = typeTag[Ret]
}
