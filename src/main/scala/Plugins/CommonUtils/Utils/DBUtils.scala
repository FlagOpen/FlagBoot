package Plugins.CommonUtils.Utils

import Plugins.CommonUtils.UserPath.chosenPath
import cats.effect.{ContextShift, IO, Timer}
import com.typesafe.config.{Config, ConfigFactory}
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DBUtils {
  import monix.execution.Scheduler.Implicits.global
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)
  lazy val DBConfig: Config = ConfigFactory
    .parseString(
      s"""
     th.properties.serverName=${chosenPath.dbServerName()},
     th.properties.portNumber=${chosenPath.dbPort().toString}
      """) withFallback (ConfigFactory.load())
  lazy val db = Database.forConfig("th", config = DBConfig)
  def exec[T] : DBIO[T] => IO[T] = action => IO.fromFuture(IO(db.run(action)))
}