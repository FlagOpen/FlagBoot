package Plugins.ClusterSystem.Extension

import Plugins.CommonUtils.TypedSystem.API.{API, APIPlanner}

/** 根据API的不同选择不同的Planner。这是在Handler里面用到的，仅用于实现ClusterAPI，即微服务之间的API。
 * 对于这样的API，我们在Plugins中定义其声明XXXAPIMessage，在Impl中定义其XXXAPIMessagePlanner，使用相应的PlannerSwitch做好对应。 */
abstract class PlannerSwitch {
  def switch[A<:API](api:A):APIPlanner[A]
}
