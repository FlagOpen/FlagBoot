package Impl.UserPlanners

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.UserAPI.UserMessages.UserRegisterMessage
import Process.IOServer
import Tables.UserTables.SlickUserTable
import cats.effect.IO
import monix.eval.Task
import doobie.implicits._

import java.util.UUID

object UserRegisterPlanner extends APIPlanner[UserRegisterMessage] {
  override protected def plan(api: UserRegisterMessage)(implicit uuid: PlanUUID, apiSender: APISender[API]): Task[String] =  Task.from {
    for {
      token <- IO (UUID.randomUUID().toString)
      _ <- SlickUserTable.addUser(api.realName, api.cellphone, api.password, token)
    } yield token
  }
}
