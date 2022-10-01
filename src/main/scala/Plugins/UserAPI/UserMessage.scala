package Plugins.UserAPI

import Plugins.ClusterSystem.Extension.ClusterAPIExtended
import Plugins.ClusterSystem.ToClusterMessages.MQRoute
import Plugins.UserAPI.UserMessages.{CheckTokenMessage, UserLoginMessage, UserRegisterMessage}
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import scala.reflect.runtime.universe._

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[CheckTokenMessage], name = "CheckTokenMessage"),
    new JsonSubTypes.Type(value = classOf[UserLoginMessage], name = "UserLoginMessage"),
    new JsonSubTypes.Type(value = classOf[UserRegisterMessage], name = "UserRegisterMessage"),
  )
)
abstract class UserMessage[Ret : TypeTag]() extends ClusterAPIExtended[Ret] {
  override def getRoute: MQRoute = MQRoute("")
}
