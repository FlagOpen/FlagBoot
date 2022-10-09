package Process

import Plugins.CommonUtils.IOManagers.HttpServerManager.createHttpServer
import Plugins.CommonUtils.IOManagers.ServiceMeshManager.pullServices
import StartUpManager.{afterInit, beforeInit}
import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.IO.ioEffect

object IOServer extends IOApp {

  import Plugins.CommonUtils.ServiceDiscovery.ServiceDiscoveryCommunicateImpls.LocalServiceDiscoveryImpl._
  override def run(args: List[String]): IO[ExitCode] = {
    beforeInit *> pullServices().background.use { _ =>
        createHttpServer(localServiceDiscoveryImpl) *> afterInit.as(ExitCode.Success).handleErrorWith(e => IO {
        e.printStackTrace()
        ExitCode.Error
      })
    }
  }
}
