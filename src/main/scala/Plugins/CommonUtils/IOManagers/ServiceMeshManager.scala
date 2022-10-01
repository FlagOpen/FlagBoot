package Plugins.CommonUtils.IOManagers

import Globals.{GlobalVariables, MainHandler}
import Plugins.CommonUtils.Utils.IOUtils
import Plugins.CommonUtils.Utils.IOUtils.resultToReply
import cats.effect.concurrent.Ref
import cats.effect.{ContextShift, ExitCode, IO, IOApp, Timer}
import org.http4s.blaze.server.BlazeServerBuilder
import monix.execution.Scheduler.Implicits.global
import org.http4s.HttpRoutes
import org.http4s.headers.Location
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.implicits._
import org.http4s.dsl.io._

import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.util.{Failure, Try}


/** Todo: 加入服务发现和管理功能 */
object ServiceMeshManager extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = ???

  lazy val pullingInterval = 5.seconds

  lazy val ref = Ref.of[IO, Map[String, List[String]]](Map())

  def pullServices() : IO[Unit] = for {
    _ <- IO.unit
    _ <- IO.sleep(pullingInterval)
   // _ <- pullServices()
  } yield ()
}
