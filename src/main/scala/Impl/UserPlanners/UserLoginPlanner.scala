package Impl.UserPlanners

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.UserAPI.UserMessages.UserLoginMessage
import Tables.UserTables.SlickUserTable
import monix.eval.Task

object UserLoginPlanner extends APIPlanner[UserLoginMessage] {
  override protected def plan(api: UserLoginMessage)(implicit uuid: PlanUUID, apiSender: APISender[API]): Task[String] = Task.from {
    SlickUserTable.checkPassword(api.cellphone, api.password)
  }
}
