import cats.effect.IO
import doobie.{ConnectionIO, Transactor}
import cats._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts._
object DoobieTest extends IOApp {

  import doobie.util.ExecutionContexts

  override def run(args: List[String]): IO[ExitCode] = {


    import doobie.util.ExecutionContexts

    // This is just for testing. Consider using cats.effect.IOApp instead of calling
    // unsafe methods directly.

    val program1 = sql"select user_name from tsmsp_portal.user".query[String].to[List]
    // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
    // on an our synchronous EC. See the chapter on connection handling for more info.
    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",     // driver classname
      "jdbc:postgresql://localhost:5432/db",     // connect URL (driver-specific)
      "db",                  // user
      "root"                           // password
    )
    program1.transact(xa).map(_.foreach(println)).as(ExitCode.Success)
    // io: IO[Int] = Uncancelable(
    //   body = cats.effect.IO$$$Lambda$12397/0x000000084304d840@2c65f453,
    //   event = cats.effect.tracing.TracingEvent$StackTrace
    // )
    // res0: Int = 42
  }

}
