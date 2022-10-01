package Plugins.CommonUtils.TypedSystem.API

import Plugins.CommonUtils.Senders.APISender
import monix.eval.Task

import scala.concurrent.duration.{DurationLong, FiniteDuration}

trait APIPlanner[A<:API] {
  /** 设置一下该任务的最长执行时间 */
  val executionTimeLimit: FiniteDuration= 30.seconds

  /** 获取API的实现计划，加上了计时功能 */
  final def getPlan(api:A)(implicit apiSender : APISender[API]): Task[A#ReturnType] = {
    plan(api)(api.uuid, apiSender).timeout(executionTimeLimit)
  }

  /** api的执行内容 */
  protected def plan(api:A)(implicit uuid:PlanUUID, apiSender : APISender[API]): Task[A#ReturnType]
}
