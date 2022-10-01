package Impl.NucleicPlanners

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.NucleicAPI.NucleicMessages.TubeAddUserMessage
import Plugins.UserAPI.UserMessages.CheckTokenMessage
import Tables.NucleicTables.TubeUserTable
import monix.eval.Task
import org.http4s.implicits.http4sLiteralsSyntax

object TubeAddUserPlanner extends APIPlanner[TubeAddUserMessage]{
  override protected def plan(api: TubeAddUserMessage)(implicit uuid: PlanUUID, apiSender: APISender[API]): Task[Boolean] =
    Task.from {
      for {
        _ <- apiSender.sendAndGet(CheckTokenMessage(api.cellphone, api.token), uri"http://localhost:8080/api")
        ret <- TubeUserTable.addTubeUser(api.tubeID, api.cellphone).map(_ > 0)
      } yield ret
    }
}
