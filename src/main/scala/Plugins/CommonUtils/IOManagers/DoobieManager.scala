//package Plugins.CommonUtils.IOManagers
//
//import Process.IOServer
//import cats.effect.{Blocker, ContextShift, IO, Resource}
//import doobie.hikari.HikariTransactor
//import doobie.implicits.toSqlInterpolator
//import doobie.util.ExecutionContexts
//import cats.effect.IO
//import doobie.{ConnectionIO, Transactor}
//import cats._
//import cats.effect._
//import cats.implicits._
//import doobie._
//import doobie.implicits._
//import doobie.util.ExecutionContexts._
//object DoobieManager {
//
//  def doobieInit() : IO[Unit] = IOServer.transactors.use {
//    xa =>
//      (for {
//        a <- sql"""CREATE SCHEMA IF NOT EXISTS flag_test""".update.run
//        b <- UserTable.create
//      } yield (a, b)).transact(xa).map(_ => ())
//  }
//
//  def createResources(blocker : Blocker)(implicit contextShift: ContextShift[IO]) : Resource[IO, HikariTransactor[IO]] =
//    for {
//      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
//      xa <- HikariTransactor.newHikariTransactor[IO](
//        "org.postgresql.Driver",                        // driver classname
//        "jdbc:postgresql://localhost:5432/db",   // connect URL
//        "db",                                   // username
//        "root",                                     // password
//        ce,
//        blocker// await connection here
//      )
//    } yield xa
//
//
//}
