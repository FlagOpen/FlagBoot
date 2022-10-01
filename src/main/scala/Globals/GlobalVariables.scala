package Globals

import Plugins.CommonUtils.ServiceCenter.userAccountServiceCode
import Plugins.NucleicAPI.NucleicMessage
object GlobalVariables {

  lazy val serviceCode = userAccountServiceCode

  type APIType = NucleicMessage[Any]

  var beforeSystemInit: () => Unit = () => {
  }

  var afterSystemInit: () => Unit = () => {
  }
}
