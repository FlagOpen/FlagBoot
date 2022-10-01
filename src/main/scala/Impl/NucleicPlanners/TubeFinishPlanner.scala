package Impl.NucleicPlanners

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.NucleicAPI.NucleicMessages.TubeFinishMessage
import Plugins.UserAPI.UserMessages.CheckTokenMessage
import Tables.NucleicTables.TubeTable
import monix.eval.Task
import org.http4s.implicits.http4sLiteralsSyntax

object TubeFinishPlanner extends APIPlanner[TubeFinishMessage] {
  override protected def plan(api: TubeFinishMessage)(implicit uuid: PlanUUID, apiSender: APISender[API]): Task[Boolean] =
    Task.from {
      for {
        _ <- apiSender.sendAndGet(CheckTokenMessage(api.cellphone, api.token), uri"http://localhost:8080/api")
        ret <- TubeTable.tubeFinish(api.tubeID, api.positive).map(_ > 0)
      } yield ret
    }
}
