package Plugins

import Plugins.ClusterSystem.Extension.ClusterAPIExtended
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}

import scala.reflect.runtime.universe._

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
  )
)
abstract class LightMessage[Ret: TypeTag] extends ClusterAPIExtended[Ret]
