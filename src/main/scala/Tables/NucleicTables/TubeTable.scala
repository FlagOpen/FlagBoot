package Tables.NucleicTables

import Tables.UserTables.SlickUserRow
import org.joda.time.DateTime
import slick.lifted.Tag
import Plugins.CommonUtils.Utils.DBUtils
import cats.effect.IO
import Plugins.CommonUtils.Types.CustomColumnTypes._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag


case class TubeTableRow(
                        tubeID : String,
                        positive : Option[Boolean],
                        initTime : Option[DateTime],
                        finishTime : Option[DateTime]
                       )

class TubeTable(tag : Tag) extends Table[TubeTableRow](tag, Some("nucleic"), "tube") {
  def tubeID = column[String]("tube_id", O.PrimaryKey)
  def positive = column[Option[Boolean]]("positive")
  def initTime = column[Option[DateTime]]("init_time")
  def finishTime = column[Option[DateTime]]("finish_time")
  def * = (tubeID, positive, initTime, finishTime).mapTo[TubeTableRow]
}

/**
 * 存储核酸试管检查结果的表
 */
object TubeTable {
  val tubeTable = TableQuery[TubeTable]

  def tubeInit(tubeID : String) : IO[Int] =
    DBUtils.exec(tubeTable += TubeTableRow(tubeID, None, Some(DateTime.now()), None))

  def tubeFinish(tubeID: String, positive : Boolean) : IO[Int] =
    DBUtils.exec(tubeTable.filter(_.tubeID === tubeID).map(t => (t.positive, t.finishTime)).update((Some(positive), Some(DateTime.now()))))

}
