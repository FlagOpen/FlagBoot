package Process

import cats.effect.IO

object StartUpManager {

  def beforeInit: IO[Unit] = IO.unit

  def afterInit: IO[Unit] = IO.unit

}
