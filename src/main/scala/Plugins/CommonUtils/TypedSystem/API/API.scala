package Plugins.CommonUtils.TypedSystem.API

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.ServiceDiscovery.ServiceCode
import Plugins.CommonUtils.Types.JacksonSerializable
import Plugins.CommonUtils.Utils.IOUtils
import cats.effect.IO
import com.fasterxml.jackson.annotation.JsonIgnore
import org.http4s.{Method, Request, Uri}
import org.joda.time.DateTime
import io.circe._
import io.circe.literal._
import io.circe.syntax._
import io.circe.generic.auto._
import io.circe.generic.semiauto._
import io.circe.parser._
import org.http4s.dsl.io.{GET, Root}

import java.util.UUID
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

/** API的基本类型，保存了API返回的数据类型 ReturnType */
abstract class API extends JacksonSerializable {
  type ReturnType

  @JsonIgnore
  def encodeToJson : String = IOUtils.serialize(this)
  @JsonIgnore
  def apiPathName : String = this.getClass.getSimpleName.toLowerCase
  @JsonIgnore
  def makeRequest(root : Uri) : Request[IO] = Request[IO](Method.GET, root / apiPathName / encodeToJson)
  @JsonIgnore
  def targetServiceCode : ServiceCode
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