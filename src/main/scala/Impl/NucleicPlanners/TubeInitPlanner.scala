package Impl.NucleicPlanners

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.NucleicAPI.NucleicMessages.TubeInitMessage
import Plugins.UserAPI.UserMessages.CheckTokenMessage
import Tables.NucleicTables.TubeTable
import monix.eval.Task
import org.http4s.implicits.http4sLiteralsSyntax

object TubeInitPlanner extends APIPlanner[TubeInitMessage] {

  override protected def plan(api: TubeInitMessage)(implicit uuid: PlanUUID, apiSender: APISender[API]): Task[Boolean] =
    Task.from {
      for {
        _ <- apiSender.sendAndGet(CheckTokenMessage(api.cellphone, api.token), uri"http://localhost:8080/api")
        ret <- TubeTable.tubeInit(api.tubeID).map(_ > 0)
      } yield ret
    }

}
