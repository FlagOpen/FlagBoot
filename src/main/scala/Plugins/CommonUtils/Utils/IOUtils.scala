package Plugins.CommonUtils.Utils

import Globals.GlobalVariables
import Plugins.CommonUtils.Exceptions.{CodeException, MessageException}
import Plugins.CommonUtils.TypedSystem.API.PlanUUID
import Plugins.CommonUtils.Types.{CollectionsTypeFactory, ReplyMessage}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import java.util
import scala.reflect.ClassTag
import scala.reflect.runtime.universe.{TypeTag, typeOf}
import scala.util.{Failure, Success, Try}


object IOUtils {
  /** Jackson使用的object mapper */
  val objectMapper: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)

  /** 序列化 */
  def serialize(o: Any): String =
    o match {
      case Some(o2) =>
        serialize(o2)
      case o2: List[_] =>
        o2.map(IOUtils.serialize).mkString("[", ",", "]")
      case _ =>
        objectMapper.writeValueAsString(o)
    }

  /** 把返回的消息变成一个reply message */
  def serializeAsReply(o: Any): ReplyMessage = ReplyMessage(0, serialize(o))


  def generateClassTag[T](className:String):ClassTag[T]= ClassTag(Class.forName(className))

  def deserialize[T:TypeTag](bytes: String)(implicit tag: ClassTag[T]): T = {
    val fullType:String=typeOf[T].toString
    if (fullType.startsWith("Map["))
      return objectMapper.readValue(bytes.getBytes(), tag.runtimeClass).asInstanceOf[T]
    fullType.count(_=='[') match {
      case 0=>
        objectMapper.readValue(bytes.getBytes(), tag.runtimeClass).asInstanceOf[T]
      case 1=>
        val subType: ClassTag[T]=generateClassTag[T](fullType.substring(fullType.indexOf('[')+1, fullType.length-1))
        tag.toString() match {
          case "scala.collection.immutable.List" | "scala.collection.Seq" =>
            deserializeList(bytes)(subType).asInstanceOf[T]
          case "scala.Option"=>
            deserializeOption(bytes)(subType).asInstanceOf[T]
        }
      case _=>
        val className=fullType.substring(fullType.lastIndexOf('[')+1, fullType.indexOf(']'))
        val subType: ClassTag[T]=generateClassTag[T](className)
        if (fullType.startsWith("List[List["))
          deserializeListList(bytes)(subType).asInstanceOf[T]
        else if (fullType.startsWith("List[Option["))
          deserializeListOption(bytes)(subType).asInstanceOf[T]
        else if (fullType.startsWith("Option[List["))
          deserializeOptionList(bytes)(subType).get.asInstanceOf[T]
        else if (fullType.startsWith("Option[Option[")) {
          deserializeOptionOption(bytes)(subType).get.asInstanceOf[T]
        } else
          objectMapper.readValue(bytes.getBytes(), tag.runtimeClass).asInstanceOf[T]
    }
  }

  def deserializeList[T](bytes:String)(implicit tag:ClassTag[T]):List[T]={
    objectMapper.readValue(bytes.getBytes(), CollectionsTypeFactory.listOf(tag.runtimeClass)).asInstanceOf[util.ArrayList[T]].
      toArray().asInstanceOf[Array[T]].toList
  }

  def deserializeOption[T](bytes:String)(implicit tag:ClassTag[T]):Option[T]={
    if (bytes == "null") None
    else Some(objectMapper.readValue(bytes.getBytes(), tag.runtimeClass).asInstanceOf[T])
  }

  def deserializeListList[T](bytes:String)(implicit tag:ClassTag[T]):List[List[T]]={
    objectMapper.readValue(bytes.getBytes(), CollectionsTypeFactory.listOfListOf(tag.runtimeClass)).
      asInstanceOf[util.ArrayList[util.ArrayList[T]]].toArray().map(_.asInstanceOf[util.ArrayList[T]].toArray().asInstanceOf[Array[T]].toList).toList
  }

  def deserializeListOption[T](bytes:String)(implicit tag:ClassTag[T]):List[Option[T]]={
    objectMapper.readValue(bytes.getBytes(), CollectionsTypeFactory.listOf(tag.runtimeClass)).asInstanceOf[util.ArrayList[AnyRef]].
      toArray().map{r=> if (r==null) None else Some(r.asInstanceOf[T])}.toList
  }

  def deserializeOptionList[T](bytes:String)(implicit tag:ClassTag[T]):Option[List[T]]={
    if (bytes=="null") None
    else Some(deserializeList(bytes)(tag))
  }

  def deserializeOptionOption[T](bytes:String)(implicit tag:ClassTag[T]):Option[Option[T]]={
    if (bytes == "null") None
    else Some(Some(objectMapper.readValue(bytes.getBytes(), tag.runtimeClass).asInstanceOf[T]))
  }


  /** 把exception变成字符串 */
  def exceptionToString(e: Throwable): String =
    e.getClass.getName + ":" +
      e.getMessage + "\n" +
      e.getStackTrace.map(_.toString).fold("")(_ + "\n" + _)

  /** 把exception变成replymessage */
  def exceptionToReply(e: Throwable): String =
    IOUtils.serialize(ReplyMessage(-1, exceptionToString(e)))

  /** 把exception变成reply message, status=2, info=code。这个方法专门用于处理运行过程中没有编码的exception，所以编码为0000，
   * 并且附上了错误的具体内容 */
  def exceptionToReplyCode(e: Throwable, uuid: String): ReplyMessage =
    ReplyMessage(-2, "0000" + exceptionToString(e), uuid)

  def resultToReply[Ret](result:Try[Ret], uuid:PlanUUID): ReplyMessage ={
    result match {
      case Success(value)=>
        ReplyMessage(0, IOUtils.serialize(value), uuid.id)
      case Failure(messageException:MessageException)=>
        ReplyMessage(-1, messageException.message, uuid.id)
      case Failure(codeException: CodeException)=>
        ReplyMessage(-2, codeException.code, uuid.id)
      case Failure(exception)=>
        exceptionToReplyCode(exception, uuid.id)
    }
  }
  def replyToResult[T](replyMessage: ReplyMessage)(implicit typeTag:TypeTag[T], classTag:ClassTag[T]):Try[T]=Try{
    replyMessage.status match {
      case 0 =>
        // TODO: 适配旧架构，之后删掉
        if (classTag.toString=="java.lang.String")
          replyMessage.info.asInstanceOf[T]
        else
          IOUtils.deserialize[T](replyMessage.info)
      case -1 => throw MessageException(replyMessage.info)
      case -2 => throw CodeException(replyMessage.info)
    }
  }
}
