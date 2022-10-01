package Plugins.ClusterSystem.Extension

import Plugins.ClusterSystem.ToClusterMessages.MQRoute
import Plugins.CommonUtils.TypedSystem.API.API
import com.fasterxml.jackson.annotation.JsonIgnore

import scala.util.Try

abstract class ClusterAPI() extends API{
  /** 指定当前的Message应该往什么地方发送消息 */
  @JsonIgnore
  def getRoute: MQRoute
}

