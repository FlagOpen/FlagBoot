package Process

import cats.effect.IO

object StartUpManager {

  def beforeInit: IO[Unit] = IO {println("Hello World")}

  def afterInit: IO[Unit] = IO.unit

}
