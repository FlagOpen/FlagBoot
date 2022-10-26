package Plugins.CommonUtils.TypedSystem.API

import Plugins.ClusterSystem.Exceptions.UnrecognizedMessage
import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.RequestHelper.HttpRequestHelper
import cats.effect.IO
import monix.eval.Task
import scala.concurrent.duration.{DurationLong, FiniteDuration}
import io.circe._
import io.circe.parser._
import org.http4s._
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
  def getRoute()(implicit APISender: APISender[API], e : Encoder[A#ReturnType], d : Decoder[A], httpHelper : HttpRequestHelper[A#RequestMethod, A]): PartialFunction[Request[IO], IO[Response[IO]]] = {
    httpHelper.getRoute(getPlan, this.suffixName)
  }

  /** api的执行内容 */
  protected def plan(api:A)(implicit uuid:PlanUUID, apiSender : APISender[API]): Task[A#ReturnType]
}
