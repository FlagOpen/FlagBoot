package Tables.UserTables

import Plugins.CommonUtils.Utils.DBUtils
import cats.effect.IO
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

case class SlickUserRow(
                    cellphone : String,
                    password : String,
                    realName : String,
                    token : String
                  )

class SlickUserTable(tag : Tag) extends Table[SlickUserRow](tag, Some("users"), "user") {
  def cellphone = column[String]("cellphone")
  def token = column[String]("token")
  def password = column[String]("password")
  def realName = column[String]("real_name")
  def * = (cellphone, password, realName, token).mapTo[SlickUserRow]
}

object SlickUserTable {
  val userTable = TableQuery[SlickUserTable]

  def checkPassword(cellphone: String, password: String): IO[String]
    = DBUtils.exec(userTable.filter(u => u.cellphone === cellphone && u.password === password).map(_.token).result).map(_.headOption.getOrElse(""))

  def addUser(realName : String, cellphone : String, password : String, token : String) : IO[Int]
    = DBUtils.exec(userTable += SlickUserRow(cellphone, password, realName, token))

  def checkToken(cellphone : String, token : String) : IO[Boolean]
    = DBUtils.exec(userTable.filter(u => u.cellphone === cellphone && u.token === token).exists.result)

  def updateToken(cellphone : String, newToken : String) : IO[Int]
    = DBUtils.exec(userTable.filter(_.cellphone === cellphone).map(_.token).update(newToken))

}