package Impl

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.TestMessages.{HelloWorldMessage, LightMessage}
import monix.eval.Task




object HelloWorldPlanner extends APIPlanner[HelloWorldMessage] {
  override protected def plan(api: HelloWorldMessage)
                             (implicit uuid: PlanUUID, apiSender: APISender[API]): Task[String] = Task {
    "Hello World!"
  }

}