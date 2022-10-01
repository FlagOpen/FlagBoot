import cats.effect.concurrent.Ref
import io.circe.{Decoder, Encoder}

object CirceTest {
  import io.circe.generic.extras.auto._
  import io.circe.generic.extras.Configuration

  implicit val genDevConfig: Configuration =
    Configuration.default.withDiscriminator("what_am_i")

  sealed trait Event

  case class Foo(i: Int) extends Event
  sealed trait Bar extends Event {
    val ps : String
  }
  case class BBar(ps: String) extends Bar
  case class Baz(c: Char) extends Event
  case class Qux(values: List[String]) extends Event
//  import io.circe.shapes
//  import shapeless.{ Coproduct, Generic }
//
//  implicit def encodeAdtNoDiscr[A, Repr <: Coproduct](implicit
//                                                      gen: Generic.Aux[A, Repr],
//                                                      encodeRepr: Encoder[Repr]
//                                                     ): Encoder[A] = encodeRepr.contramap(gen.to)
//
//  implicit def decodeAdtNoDiscr[A, Repr <: Coproduct](implicit
//                                                      gen: Generic.Aux[A, Repr],
//                                                      decodeRepr: Decoder[Repr]
//                                                     ): Decoder[A] = decodeRepr.map(gen.from)
  def main(args : Array[String]) : Unit = {
    import io.circe.parser.decode, io.circe.syntax._

    Ref
    println(BBar("1234566").asJson.noSpaces)
    println(decode[Event]("""{ "ps": "123456", "what_am_i": "BBar" }"""))

  }

}
