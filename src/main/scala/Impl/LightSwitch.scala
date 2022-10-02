package Impl

import Plugins.ClusterSystem.Exceptions.APIMessageExecutorNotFoundException
import Plugins.ClusterSystem.Extension.PlannerSwitch
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner}


object LightSwitch extends PlannerSwitch {
  override def switch[A <: API](api: A): APIPlanner[A] = {
    (api match {
      case _ => throw APIMessageExecutorNotFoundException()
    }).asInstanceOf[APIPlanner[A]]
  }
}
