package Plugins.CommonUtils.IOManagers

import cats.effect.concurrent.Ref
import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.duration.{DurationInt, FiniteDuration}


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
