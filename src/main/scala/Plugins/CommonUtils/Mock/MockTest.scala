package Plugins.CommonUtils.Mock

import Plugins.CommonUtils.Exceptions.NotEnoughTestMessageException

object MockTest {
  var testPointer:Int=0
  var testMessage:List[String]=List()
  def getNextTestMessage:String={
    if (testPointer>=testMessage.length)
      throw NotEnoughTestMessageException()
    testPointer+=1
    testMessage(testPointer-1)
  }

}
