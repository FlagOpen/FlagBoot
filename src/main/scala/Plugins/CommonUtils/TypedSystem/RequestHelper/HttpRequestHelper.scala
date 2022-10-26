package Plugins.CommonUtils.TypedSystem.RequestHelper

import Plugins.ClusterSystem.Exceptions.UnrecognizedMessage
import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.API
import Plugins.CommonUtils.TypedSystem.RequestMethod.{DefaultGetMethod, DefaultPostMethod}
import cats.effect.IO
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.{Decoder, Encoder}
import io.circe.syntax._
import monix.eval.Task
import org.http4s.Method.POST
import org.http4s.{Method, Request, Response, Uri}
import org.http4s.client.dsl.io._
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import monix.execution.Scheduler.Implicits.global

import java.net.{URLDecoder, URLEncoder}

trait HttpRequestHelper[M, A <: API] extends RequestHelper[M, A] {
  def encodeToJson(api : A) : String
  def decodeJson(str: String): IO[A]
  def apiPathName(api : A) : String
  def makeRequest(api : A, root : Uri) : Request[IO]
  def getRoute(getPlan : A => Task[A#ReturnType], pathName : String)(implicit e : Encoder[A#ReturnType]) : PartialFunction[Request[IO], IO[Response[IO]]]
}

object HttpRequestHelper {

  implicit def httpPostRequestHelper[A <: API](implicit encoder : Encoder[A], decoder : Decoder[A], apiSender : APISender[API]): HttpPostRequestHelper[A] = HttpPostRequestHelper[A]
  implicit def httpGetRequestHelper[A <: API](implicit encoder : Encoder[A], decoder : Decoder[A], apiSender : APISender[API]): HttpGetRequestHelper[A] = HttpGetRequestHelper[A]
  case class HttpPostRequestHelper[A <: API]()(implicit encoder : Encoder[A], decoder : Decoder[A], apiSender : APISender[API]) extends HttpRequestHelper[DefaultPostMethod, A] {

    override def encodeToJson(api: A): String = api.asJson.noSpaces
    override def decodeJson(str: String): IO[A] = IO(decode[A](str).getOrElse(throw UnrecognizedMessage(str)))
    override def apiPathName(api: A): String = api.getClass.getSimpleName.toLowerCase
    override def makeRequest(api: A, root: Uri): Request[IO] = POST(raw"${this.encodeToJson(api)}", root / this.apiPathName(api))

    override def getRoute(getPlan : A => Task[A#ReturnType], pathName : String)(implicit e : Encoder[A#ReturnType]): PartialFunction[Request[IO], IO[Response[IO]]] = {
        case req @ POST -> Root / `pathName` =>
          (for {
            api <- req.as[String].flatMap(decodeJson)
            result <- getPlan(api).to[IO]
          } yield result)
            .flatMap(k => Ok(k))
            .handleErrorWith(e => BadRequest(e.getMessage))

    }
  }

  case class HttpGetRequestHelper[A <: API]()(implicit encoder : Encoder[A], decoder : Decoder[A], apiSender : APISender[API]) extends HttpRequestHelper[DefaultGetMethod, A] {
    override def encodeToJson(api: A): String = URLEncoder.encode(api.asJson.noSpaces, "UTF8")
    override def decodeJson(str: String): IO[A] = IO(decode[A](URLDecoder.decode(str, "UTF8")).getOrElse(throw UnrecognizedMessage(str)))
    override def apiPathName(api: A): String = api.getClass.getSimpleName.toLowerCase
    override def makeRequest(api: A, root: Uri): Request[IO] = Method.GET(root / this.apiPathName(api) / this.encodeToJson(api))

    override def getRoute(getPlan: A => Task[A#ReturnType], pathName: String)(implicit e: Encoder[A#ReturnType]): PartialFunction[Request[IO], IO[Response[IO]]] = {
      case GET -> Root / `pathName` / content =>
        (for {
          api <- decodeJson(content)
          result <- getPlan(api).to[IO]
        } yield result)
          .flatMap(k => Ok(k))
          .handleErrorWith(e => BadRequest(e.getMessage))

    }

  }

}
