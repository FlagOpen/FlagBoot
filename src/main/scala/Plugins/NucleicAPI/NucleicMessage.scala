package Plugins.NucleicAPI

import Plugins.ClusterSystem.Extension.ClusterAPIExtended
import Plugins.ClusterSystem.ToClusterMessages.MQRoute
import Plugins.NucleicAPI.NucleicMessages.{TubeAddUserMessage, TubeFinishMessage, TubeInitMessage, UserCheckNucleinMessage}
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonSubTypes, JsonTypeInfo}

import scala.reflect.runtime.universe._
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[TubeAddUserMessage], name = "TubeAddUserMessage"),
    new JsonSubTypes.Type(value = classOf[TubeFinishMessage], name = "TubeFinishMessage"),
    new JsonSubTypes.Type(value = classOf[TubeInitMessage], name = "TubeInitMessage"),
    new JsonSubTypes.Type(value = classOf[UserCheckNucleinMessage], name = "UserCheckNucleinMessage"),
  )
)
abstract class NucleicMessage[Ret: TypeTag]() extends ClusterAPIExtended[Ret] {
  @JsonIgnore
  override def getRoute: MQRoute = MQRoute("")
}
