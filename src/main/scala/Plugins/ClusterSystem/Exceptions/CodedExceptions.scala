package Plugins.ClusterSystem.Exceptions

import Plugins.CommonUtils.Exceptions.ExceptionWithCode

case class APIMessageExecutorNotFoundException(errMsg: String = "") extends ExceptionWithCode{
  override val code: String = "01"
}

case class ReplyMessageUUIDRouteNotFoundException(uuid:String) extends ExceptionWithCode{
  override val code: String = "02-${uuid}"
}

