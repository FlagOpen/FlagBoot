package Plugins.CommonUtils.Senders
import Plugins.CommonUtils.TypedSystem.API.API
import Plugins.CommonUtils.Types.ReplyMessage
import Plugins.CommonUtils.Utils.IOUtils
import Plugins.CommonUtils.Utils.IOUtils.{replyToResult, serialize}
import cats.effect.{IO, Resource}
import org.http4s.client.Client
import org.http4s.circe._
import org.http4s.client.dsl.io._
import io.circe.literal._
import org.http4s.Method.POST
import org.http4s._
import io.circe.generic.auto._

import scala.reflect.runtime.universe.{TypeTag, typeOf}
import scala.language.higherKinds
import scala.reflect.ClassTag
import scala.reflect.runtime.universe

trait APISender[A <: API] {
  def sendAndGet[B <: A](a : B, uri : Uri)(implicit typeTag:TypeTag[B#ReturnType], classTag:ClassTag[B#ReturnType]) : IO[B#ReturnType]
  def sendAndGetType[T](a : A, uri : Uri)(implicit typeTag:TypeTag[T], classTag:ClassTag[T]) : IO[T]
}

object APISender {

  implicit val replyDecoder: EntityDecoder[IO, ReplyMessage] = jsonOf[IO, ReplyMessage]

  case class HttpAPISender[A <: API]()(implicit clientResource : Resource[IO, Client[IO]]) extends APISender[A] {

    override def sendAndGet[B <: A](a : B, uri : Uri)(implicit typeTag:TypeTag[B#ReturnType], classTag:ClassTag[B#ReturnType]): IO[B#ReturnType] = sendAndGetType[B#ReturnType](a, uri)

    override def sendAndGetType[T](a: A, uri: Uri)(implicit typeTag: universe.TypeTag[T], classTag: ClassTag[T]): IO[T] = {
      clientResource.use { client =>
        for {
          serialized <- IO(IOUtils.serialize(a))
          reply <- client.expect[String](POST(raw"${serialized}", uri)).map(IOUtils.deserialize[ReplyMessage])
          ret <- IO.fromTry[T](replyToResult[T](reply))
        } yield ret
      }
    }
  }
}
