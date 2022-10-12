package Plugins.CommonUtils.IOManagers

import Globals.{GlobalVariables, MainHandler}
import Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryCommunicateImpls.ServiceDiscoveryCommunicateImpl
import Plugins.CommonUtils.ServiceDiscovery.{ServiceDiscoveryCommunicateMode, ServiceDiscoveryHostnameInfoMode, ServiceDiscoveryInfoMode}
import Plugins.CommonUtils.TypedSystem.API.API
import cats.data.Kleisli
import cats.effect.{ContextShift, IO, Resource, Timer}
import org.http4s.blaze.server.BlazeServerBuilder
import monix.execution.Scheduler.Implicits.global
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import org.http4s.{HttpRoutes, Request, Response}
import org.http4s.implicits._
import org.http4s.server.Router

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object HttpServerManager {

  lazy val processTimeOut: FiniteDuration = 30.seconds

  import Plugins.CommonUtils.Senders.APISender.HttpAPISender
  /** Todo: Reflect + route per api*/
  def httpService(implicit client : Client[IO],
                  discoveryImpl : ServiceDiscoveryCommunicateImpl[ServiceDiscoveryCommunicateMode, ServiceDiscoveryHostnameInfoMode, String, String]):
  Kleisli[IO, Request[IO], Response[IO]] =
    Router(Plugins.CommonUtils.Configs.HttpServerConfig.config.apiPath -> GlobalVariables.mainRoutes(HttpAPISender[API])).orNotFound
//    HttpRoutes.of[IO] {
//    case GET -> Root / "user" =>
//      MainHandler()(HttpAPISender[API]).process(CheckTokenMessage("","")).timeout(processTimeOut).to[IO].handleErrorWith(e => IO(e.getMessage)).flatMap(f => Ok("success-user3"))
//    case req @ POST -> Root / "api" =>
//      req.as[String].flatMap { reqString =>
//        IO(IOUtils.deserialize[GlobalVariables.APIType](reqString))
//      }.flatMap { api =>
//        for {
//          result <- MainHandler()(HttpAPISender[API]).process(api).timeout(processTimeOut).to[IO].map(Try(_)).handleErrorWith(e => IO (Failure(e)))
//          t <- IO(resultToReply[api.ReturnType](result, api.uuid))
//          s <- IO(IOUtils.serialize(t))
//        } yield s
//      }.handleErrorWith(e => IO {
//        e.printStackTrace()
//        e.getMessage
//      }).flatMap(Ok(_))
//    case GET -> Root / "user2" =>
//      Ok("success-user2")
//  }.orNotFound

  def createHttpServer(discoveryImpl : ServiceDiscoveryCommunicateImpl[ServiceDiscoveryCommunicateMode, ServiceDiscoveryHostnameInfoMode, String, String])
                      (implicit context : ContextShift[IO], timer : Timer[IO]) : IO[Unit] = {
    for {
      clientResource <- IO(BlazeClientBuilder[IO](global).resource)
      t <- clientResource.use {
        client =>
          BlazeServerBuilder[IO](global)
            .bindHttp(Plugins.CommonUtils.Configs.HttpServerConfig.config.port,Plugins.CommonUtils.Configs.HttpServerConfig.config.hostname)
            .withHttpApp(httpService(client, discoveryImpl))
            .serve
            .compile
            .drain.handleErrorWith(e => IO(e.printStackTrace()))
      }
    } yield t
  }
}
