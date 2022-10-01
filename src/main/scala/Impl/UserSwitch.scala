package Impl

import Impl.UserPlanners.{CheckTokenPlanner, UserLoginPlanner, UserRegisterPlanner}
import Plugins.ClusterSystem.Exceptions.APIMessageExecutorNotFoundException
import Plugins.ClusterSystem.Extension.PlannerSwitch
import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner}
import Plugins.UserAPI.UserMessages.{CheckTokenMessage, UserLoginMessage, UserRegisterMessage}

object UserSwitch extends PlannerSwitch{
  override def switch[A <: API](api: A): APIPlanner[A] = {
    (api match {
      case _ : UserRegisterMessage => UserRegisterPlanner
      case _ : UserLoginMessage => UserLoginPlanner
      case _ : CheckTokenMessage => CheckTokenPlanner
      case _ => throw APIMessageExecutorNotFoundException()
    }).asInstanceOf[APIPlanner[A]]
  }
}
