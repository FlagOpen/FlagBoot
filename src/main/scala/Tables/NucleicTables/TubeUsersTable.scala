package Tables.NucleicTables

import Tables.UserTables.SlickUserRow
import org.joda.time.DateTime
import slick.lifted.Tag
import Plugins.CommonUtils.Utils.DBUtils
import cats.effect.IO
import Plugins.CommonUtils.Types.CustomColumnTypes._
import com.fasterxml.jackson.databind.util.ArrayBuilders.DoubleBuilder
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag


case class TubeUserTableRow(
                         tubeID : String,
                         userCellphone : String,
                         userCheckTime : DateTime
                       )

class TubeUserTable(tag : Tag) extends Table[TubeUserTableRow](tag, Some("nucleic"), "tube_user") {
  def tubeID = column[String]("tube_id")
  def userCellphone = column[String]("user_cellphone")
  def userCheckTime = column[DateTime]("user_check_time")
  def * = (tubeID, userCellphone, userCheckTime).mapTo[TubeUserTableRow]
}

/**
 * 存储核酸试管检查结果的表
 */
object TubeUserTable {
  val tubeUserTable = TableQuery[TubeUserTable]

  def addTubeUser(tubeID : String, userCellphone : String) : IO[Int] =
    DBUtils.exec(tubeUserTable += TubeUserTableRow(tubeID, userCellphone, DateTime.now()))

}
