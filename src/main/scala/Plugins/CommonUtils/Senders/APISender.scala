package Plugins.CommonUtils.Senders

import Plugins.CommonUtils.Exceptions.HostnameNotFoundException
import Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryCommunicateImpls.ServiceDiscoveryCommunicateImpl
import Plugins.CommonUtils.ServiceDiscovery.{ServiceDiscoveryCommunicateMode, ServiceDiscoveryHostnameInfoMode}
import Plugins.CommonUtils.TypedSystem.API.API
import cats.effect.{IO, Resource}
import io.circe.Decoder
import org.http4s.client.Client
import org.http4s.circe._
import org.http4s._

import scala.language.higherKinds

trait APISender[A <: API] {
  def sendAndGet[B <: A](a : B)(implicit d :Decoder[B#ReturnType]) : IO[B#ReturnType]
  def sendAndGetType[T](a : A)(implicit d :Decoder[T]) : IO[T]
}

object APISender {

  case class HttpAPISender[A <: API]()(implicit client : Client[IO],
                                       discoveryImpl : ServiceDiscoveryCommunicateImpl[ServiceDiscoveryCommunicateMode, ServiceDiscoveryHostnameInfoMode, String, String]
  ) extends APISender[A] {

    override def sendAndGet[B <: A](a : B)(implicit d :Decoder[B#ReturnType]): IO[B#ReturnType] = sendAndGetType[B#ReturnType](a)

    override def sendAndGetType[T](a: A)(implicit d :Decoder[T]): IO[T] = {
        for {
          fwd <- discoveryImpl.getInfo(a.targetServiceCode)
          uri <- IO(Uri.fromString(fwd).getOrElse(throw HostnameNotFoundException()))
          ret <- client.expect(a.makeRequest(uri))(jsonOf[IO, T])
        } yield ret
    }
  }
}
