package Plugins.CommonUtils.Utils

import java.util.concurrent.ScheduledThreadPoolExecutor

import monix.execution.{ExecutionModel, Scheduler}
import monix.execution.schedulers.AsyncScheduler

import scala.concurrent.ExecutionContext

/**
 * @see [[monix.execution.schedulers.SchedulerCompanionImpl]]
 */
object TypedUtils {

  def getSingleThreadExecutors: Scheduler = {
    AsyncScheduler(new ScheduledThreadPoolExecutor(1), ExecutionContext.global, ExecutionModel.Default)
  }

}
