package Plugins

import akka.util.Timeout

import java.util.concurrent.TimeUnit

package object ClusterSystem{
  val askTimeOut: Timeout = Timeout(90, TimeUnit.SECONDS)
}
