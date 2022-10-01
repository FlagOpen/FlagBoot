package Plugins.CommonUtils.Types

import scala.reflect.ClassTag

abstract class IDClass(val v:Long) extends JacksonSerializable{
  def toType[T<:IDClass](implicit c:ClassTag[T]):T=
    c.runtimeClass.getConstructors.head.newInstance(v.asInstanceOf[Object]).asInstanceOf[T]
  def vInt:Int=v.toInt
}
