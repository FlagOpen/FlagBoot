package Plugins.ClusterSystem.Extension

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.API
import monix.eval.Task

/** 基本的处理API的逻辑，来了API之后，找到相对应的PlannerSwitch，然后切换一下 */
abstract class BaseAPIHandler(plannerSwitch: PlannerSwitch)(implicit apiSender : APISender[API]) {
  def process[A<:API](api:A) : Task[A#ReturnType] =
    plannerSwitch.switch(api).getPlan(api)
}
