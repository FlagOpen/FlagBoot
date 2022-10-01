package Plugins.ClusterSystem.Exceptions

import Plugins.CommonUtils.Exceptions.{ExceptionWithCode, PluginsExceptionCode}

case class APIMessageExecutorNotFoundException(errMsg: String = "") extends ExceptionWithCode{
  override val code: String =  PluginsExceptionCode.clusterSystemExceptionCodePrefix+"01"
}

case class ReplyMessageUUIDRouteNotFoundException(uuid:String) extends ExceptionWithCode{
  override val code: String =  PluginsExceptionCode.clusterSystemExceptionCodePrefix+s"02-${uuid}"
}

