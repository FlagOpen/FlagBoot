package Impl

import Impl.NucleicPlanners.{TubeAddUserPlanner, TubeFinishPlanner, TubeInitPlanner, UserCheckNucleinPlanner}
import Plugins.ClusterSystem.Exceptions.APIMessageExecutorNotFoundException
import Plugins.ClusterSystem.Extension.PlannerSwitch
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner}
import Plugins.NucleicAPI.NucleicMessages.{TubeAddUserMessage, TubeFinishMessage, TubeInitMessage, UserCheckNucleinMessage}

object NucleicSwitch extends PlannerSwitch{
  override def switch[A <: API](api: A): APIPlanner[A] = {
    (api match {
      case _ : TubeInitMessage => TubeInitPlanner
      case _ : TubeFinishMessage => TubeFinishPlanner
      case _ : TubeAddUserMessage => TubeAddUserPlanner
      case _ : UserCheckNucleinMessage => UserCheckNucleinPlanner
      case _ => throw APIMessageExecutorNotFoundException()
    }).asInstanceOf[APIPlanner[A]]
  }
}
