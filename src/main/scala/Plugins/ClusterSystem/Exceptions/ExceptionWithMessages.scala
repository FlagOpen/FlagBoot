package Plugins.ClusterSystem.Exceptions

import Plugins.ClusterSystem.ToClusterMessages.MQRoute
import Plugins.CommonUtils.Exceptions.ExceptionWithMessage
import Plugins.CommonUtils.TypedSystem.API.API


case class WrongToClusterMessageType() extends ExceptionWithMessage {
  override val message: String = "错误：ToCluster Message类型错误！"
}

case class WrapFunctionNotInitializedException() extends ExceptionWithMessage{
  override val message: String=s"错误：WrapFunction没有初始化！"
}

case class NoToClusterMessageWrapException(api:API) extends ExceptionWithMessage{
  override val message: String=s"错误：没有找到当前消息${api.getClass.getName}的wrap！"
}

case class RouteNotAcceptedException(routeName:MQRoute) extends ExceptionWithMessage{
  override val message: String=s"错误：尝试调用的微服务路由${routeName.name}没有开放权限！"
}

case class ServiceCodeNotFoundInRouteMapException(serviceCode:String) extends ExceptionWithMessage{
  override val message: String = s"错误：serviceCode:${serviceCode}没有加到routeMap中"
}
