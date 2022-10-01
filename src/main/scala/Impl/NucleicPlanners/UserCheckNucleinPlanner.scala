package Impl.NucleicPlanners

import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner, PlanUUID}
import Plugins.NucleicAPI.NucleicMessages.UserCheckNucleinMessage
import Plugins.UserAPI.UserMessages.CheckTokenMessage
import Tables.NucleicTables.{TubeTable, TubeUserTable}
import monix.eval.Task
import org.http4s.implicits.http4sLiteralsSyntax
import Plugins.CommonUtils.Types.CustomColumnTypes._
import Plugins.CommonUtils.Utils.DBUtils
import slick.jdbc.PostgresProfile.api._

object UserCheckNucleinPlanner extends APIPlanner[UserCheckNucleinMessage] {
  override protected def plan(api: UserCheckNucleinMessage)(implicit uuid: PlanUUID, apiSender: APISender[API]): Task[Option[Boolean]] =
    Task.from {
      for {
        _ <- apiSender.sendAndGet(CheckTokenMessage(api.cellphone, api.token), uri"http://localhost:8080/api")
        ret <-
          DBUtils.exec(
            TubeTable.tubeTable.filter(_.positive.nonEmpty).join(
              TubeUserTable.tubeUserTable.filter(_.userCellphone === api.cellphone)
            ).on((tube, tubeUser) => tube.tubeID === tubeUser.tubeID).sortBy(_._1.finishTime.desc).map(_._1.positive).take(1).result
          ).map(_.headOption.flatten)
      } yield ret
    }
}
