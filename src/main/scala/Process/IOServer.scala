package Process

import Plugins.CommonUtils.IOManagers.HttpServerManager.createHttpServer
import Plugins.CommonUtils.IOManagers.ServiceMeshManager.pullServices
import Plugins.CommonUtils.IOManagers.StartUpManager.{afterInit, beforeInit}
import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.IO.ioEffect

object IOServer extends IOApp {

//  lazy val transactors: Resource[IO, HikariTransactor[IO]] = for {
//      blocker <- Blocker[IO]
//      transactor <- DoobieManager.createResources(blocker)
//    } yield transactor

  override def run(args: List[String]): IO[ExitCode] = {
    beforeInit *> pullServices().background.use { _ =>
        createHttpServer() *> afterInit.as(ExitCode.Success).handleErrorWith(e => IO {
        e.printStackTrace()
        ExitCode.Error
      })
    }
  }
}
