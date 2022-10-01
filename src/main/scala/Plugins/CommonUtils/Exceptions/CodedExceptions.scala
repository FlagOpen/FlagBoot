package Plugins.CommonUtils.Exceptions

import Globals.GlobalVariables

abstract class ExceptionWithCode extends Exception{
  val code:String
  override def getMessage: String = ""+getFullCode
  def getFullCode:String= code
}

/** 传递某种code exception */
case class CodeException(override val code: String="EEEE") extends ExceptionWithCode

/** 错误：未知ReplyMessage错误！ */
case class UnknownReplyMessageTypeException() extends ExceptionWithCode{
  override val code: String= PluginsExceptionCode.commonUtilsExceptionCodePrefix+"00"
}

/** 错误：返回的消息类型错误！ */
case class WrongReturnMessageTypeException() extends ExceptionWithCode{
  override val code: String= PluginsExceptionCode.commonUtilsExceptionCodePrefix+"01"
}

/** 出错了：test message 个数太少了，请加一些额外的message，不然无法测试！ */
case class NotEnoughTestMessageException() extends ExceptionWithCode{
  override val code: String = PluginsExceptionCode.commonUtilsExceptionCodePrefix +"02"
}
/** 逆序列化出错了 */
case class DeserializeException() extends ExceptionWithCode {
  override val code: String = PluginsExceptionCode.commonUtilsExceptionCodePrefix +"03"
}
/** 有Thread的名字一样 */
case class SameThreadNameException() extends ExceptionWithCode {
  override val code: String = PluginsExceptionCode.commonUtilsExceptionCodePrefix +"04"
}

case class WrongUserTokenException() extends ExceptionWithCode{
  override val code: String = PluginsExceptionCode.commonUtilsExceptionCodePrefix +"05"
}
case class DeleteFileException() extends ExceptionWithCode{
  override val code: String = PluginsExceptionCode.commonUtilsExceptionCodePrefix +"06"
}
