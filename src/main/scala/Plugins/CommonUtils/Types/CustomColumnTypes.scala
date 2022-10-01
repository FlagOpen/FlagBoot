package Plugins.CommonUtils.Types

import Plugins.CommonUtils.Utils.IOUtils

import java.sql.Timestamp
import org.joda.time.DateTime
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._
import scala.reflect.runtime.universe.TypeTag

import scala.reflect.ClassTag

/** 用于数据库的自动类型转换 */
object CustomColumnTypes {
  implicit val jodaDateTimeType: JdbcType[DateTime] with BaseTypedType[DateTime] =
    MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime)
    )

  implicit def listType[T](implicit tag: ClassTag[T]): JdbcType[List[T]] with BaseTypedType[List[T]] =
    MappedColumnType.base[List[T], String](
      a => {
        IOUtils.serialize(a)
      },
      a => {
        IOUtils.deserializeList[T](a)
      }
    )

  implicit def jacksonSerializableType[T <: JacksonSerializable:TypeTag](implicit c: ClassTag[T]): JdbcType[T] with BaseTypedType[T] = {
    MappedColumnType.base[T, String](
      IOUtils.serialize(_),
      IOUtils.deserialize[T](_)
    )
  }

  implicit def longType[T <: IDClass](implicit c: ClassTag[T]): JdbcType[T] with BaseTypedType[T] =
    MappedColumnType.base[T, Long](
      _.v,
      a => {
        c.runtimeClass.getConstructors.head.newInstance(a.asInstanceOf[Object]).asInstanceOf[T]
      }
    )

}
