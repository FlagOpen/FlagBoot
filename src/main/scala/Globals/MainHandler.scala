package Globals

import Impl.{NucleicSwitch, UserSwitch}
import Plugins.ClusterSystem.Extension.BaseAPIHandler
import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.API

case class MainHandler(implicit apiSender : APISender[API]) extends BaseAPIHandler(NucleicSwitch)(apiSender)
