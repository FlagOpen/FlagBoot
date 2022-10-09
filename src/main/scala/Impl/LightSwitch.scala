package Impl

import Plugins.ClusterSystem.Exceptions.APIMessageExecutorNotFoundException
import Plugins.ClusterSystem.Extension.PlannerSwitch
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner}
import Plugins.TestMessages.HelloWorldMessage


object LightSwitch extends PlannerSwitch {
  override def switch[A <: API](api: A): APIPlanner[A] = {
    (api match {
      case _ : HelloWorldMessage => HelloWorldPlanner
      case _ => throw APIMessageExecutorNotFoundException()
    }).asInstanceOf[APIPlanner[A]]
  }
}
