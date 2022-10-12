package Plugins.ClusterSystem.Exceptions

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

case class UnrecognizedMessage(msg :String) extends ExceptionWithMessage {
  override val message: String = s"错误：无法识别的消息${msg}"
}
