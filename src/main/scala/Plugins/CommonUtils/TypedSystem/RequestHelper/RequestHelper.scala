package Plugins.CommonUtils.TypedSystem.RequestHelper

import Plugins.CommonUtils.TypedSystem.API.API
import cats.effect.IO
import org.http4s.{Request, Uri}

/**
 *
 * @tparam M Request Method Type (e.g. DefaultPostMethod)
 * @tparam A API Type
 */
trait RequestHelper[M, A <: API]