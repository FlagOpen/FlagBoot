package Plugins.CommonUtils.IOManagers

import Plugins.CommonUtils.Utils.DBUtils
import Plugins.CommonUtils.{LocalTestPath, UserPath}
import Tables.NucleicTables.{TubeTable, TubeUserTable}
import Tables.UserTables.SlickUserTable
import cats.effect.IO
import monix.eval.Task
import slick.dbio.DBIO
import slick.jdbc.PostgresProfile.api._

object StartUpManager {

  def beforeInit : IO[Unit] =
    for {
      _ <- IO {UserPath.chosenPath = LocalTestPath()}
      _ <- DBUtils.exec(DBIO.seq(
          sql"CREATE SCHEMA IF NOT EXISTS nucleic".as[Long],
          TubeTable.tubeTable.schema.createIfNotExists,
          TubeUserTable.tubeUserTable.schema.createIfNotExists
        ))
    } yield ()

  def afterInit :  IO[Unit] = IO.unit

}
