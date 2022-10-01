package Plugins.CommonUtils.TypedSystem.API

import Plugins.CommonUtils.Types.JacksonSerializable
import com.fasterxml.jackson.annotation.JsonIgnore
import org.joda.time.DateTime

import java.util.UUID
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

/** API的基本类型，保存了API返回的数据类型 ReturnType */
abstract class API() extends JacksonSerializable {
  type ReturnType
  /// TODO: 暂时注释，等重构完成再去掉
  @JsonIgnore
  var uuid:PlanUUID=PlanUUID("")
  @JsonIgnore
  def getReturnTypeTag:TypeTag[ReturnType]
  @JsonIgnore
  def getReturnClassTag:ClassTag[ReturnType]= {
    val tag=getReturnTypeTag
    ClassTag[ReturnType](tag.mirror.runtimeClass(tag.tpe))
  }

  def withUUID(uuid:String):this.type={
    this.uuid= PlanUUID(uuid)
    this
  }

  /** 表示当前API是否会产生回复。如果不会产生回复，则这里会复写成false */
  def hasReply:Boolean= API.hasReply(getReturnClassTag.toString())
}

object API{
  /** 初始化uuid */
  def initUUID():String= DateTime.now().getMillis.toString + ":" + UUID.randomUUID().toString.take(10)

  def hasReply(className:String):Boolean= {
    className!="Unit" && className!="Nothing"
  }
}