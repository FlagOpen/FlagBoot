package Plugins.CommonUtils.Exceptions


abstract class ExceptionWithCode extends Exception{
  val code:String
  override def getMessage: String = ""+getFullCode
  def getFullCode:String= code
}
/** 传递某种code exception */
case class CodeException(override val code: String="EEEE") extends ExceptionWithCode
