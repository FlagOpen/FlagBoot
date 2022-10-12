package Globals

import Impl.LightSwitch
import Plugins.ClusterSystem.Extension.BaseAPIHandler
import Plugins.CommonUtils.Senders.APISender
import Plugins.CommonUtils.TypedSystem.API.API

case class MainHandler()(implicit apiSender : APISender[API]) extends BaseAPIHandler(LightSwitch)(apiSender)
