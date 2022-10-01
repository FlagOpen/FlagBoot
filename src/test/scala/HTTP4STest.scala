
import cats.effect.IOApp
import monix.execution.Scheduler.Implicits.global
import org.http4s.headers.Location

object HTTP4STest extends IOApp {

  import cats.effect._, org.http4s._, org.http4s.dsl.io._
  import com.comcast.ip4s._
  import org.http4s.ember.server._
  import org.http4s.implicits._
  import org.http4s.server.Router
  import scala.concurrent.duration._
  import org.http4s.blaze.server._

  def httpService = HttpRoutes.of[IO] {
    case _ -> Root =>
      TemporaryRedirect(Location(uri"http://www.baidu.com/"))
    case GET -> Root / "test2" =>
      Ok("success")
  }.orNotFound

  def run(args : List[String]) : IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(httpService)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
