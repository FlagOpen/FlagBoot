package Impl.UserPlanners

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.UserAPI.UserMessages.CheckTokenMessage
import Tables.UserTables.SlickUserTable
import monix.eval.Task

object CheckTokenPlanner extends APIPlanner[CheckTokenMessage] {
  override protected def plan(api: CheckTokenMessage)(implicit uuid: PlanUUID, apiSender: APISender[API]): Task[Boolean] = Task.from {
    SlickUserTable.checkToken(api.cellphone, api.token)
  }
}
