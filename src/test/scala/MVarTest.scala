
import monix.catnap.MVar
import monix.eval.Task
import monix.execution.CancelableFuture
import monix.catnap.MVar
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
object MVarTest {
  final class MLock(mvar: MVar[Task, Unit]) {
    def acquire: Task[Unit] =
      mvar.take

    def release: Task[Unit] =
      mvar.put(())

    def greenLight[A](fa: Task[A]): Task[A] =
      for {
        _ <- acquire
        a <- fa.doOnCancel(release)
        _ <- release
      } yield a
  }

  object MLock {
    def apply(): Task[MLock] =
      MVar[Task].of(()).map(v => new MLock(v))
  }
  def main(args : Array[String]) : Unit = {
    val t1 = DateTime.now().getMillis
    val locks : Array[MLock] = (1 to 100000).toArray.map(_ => MLock()).map(l => Await.result(l.runToFuture, 3.seconds))
    val t2 = DateTime.now().getMillis
    (1 to 10000000).foreach(i => locks(i % 100000).greenLight[String](Task {i.toString}).runToFuture.foreach(_ => if (i % 99999 == 0) println(i)))
    val t3 = DateTime.now().getMillis
    println(t3 - t2)
    println(t2 - t1)
  }

}
