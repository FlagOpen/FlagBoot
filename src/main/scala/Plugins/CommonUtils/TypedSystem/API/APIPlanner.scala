package Plugins.CommonUtils.TypedSystem.API

import Globals.MainHandler
import Plugins.ClusterSystem.Exceptions.UnrecognizedMessage
import Plugins.CommonUtils.IOManagers.HttpServerManager.processTimeOut
import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.Senders.APISender.HttpAPISender
import cats.effect.IO
import monix.eval.Task
import org.http4s.HttpRoutes
import monix.execution.Scheduler.Implicits.global

import scala.reflect.runtime.universe._
import scala.concurrent.duration.{DurationLong, FiniteDuration}
import cats.effect._
import io.circe._
import io.circe.parser._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe._
import io.circe.syntax._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder

import scala.reflect.runtime.universe
import scala.util.{Failure, Try}
trait APIPlanner[A <:API] {
  /** 设置一下该任务的最长执行时间 */
  val executionTimeLimit: FiniteDuration= 30.seconds

  /** 获取API的实现计划，加上了计时功能 */
  final def getPlan(api:A)(implicit apiSender : APISender[API]): Task[A#ReturnType] = {
    plan(api)(api.uuid, apiSender).timeout(executionTimeLimit)
  }

  lazy val suffixName = this.getClass.getSimpleName.replace("Planner", "Message").toLowerCase.dropRight(1)
  def decodeJson(str : String)(implicit d : Decoder[A]) : IO[A] =
    IO(decode[A](str).getOrElse(throw UnrecognizedMessage(str)))
  def getRoute()(implicit APISender: APISender[API], e : Encoder[A#ReturnType], d : Decoder[A]): PartialFunction[Request[IO], IO[Response[IO]]] = {
    case req @ POST -> Root / this.suffixName =>
      (for {
        api <- req.as[String].flatMap(decodeJson)
        result <- getPlan(api).to[IO]
      } yield result)
        .flatMap(k => Ok(k)).handleErrorWith(e => BadRequest(e.getMessage))
  }

  /** api的执行内容 */
  protected def plan(api:A)(implicit uuid:PlanUUID, apiSender : APISender[API]): Task[A#ReturnType]
}
