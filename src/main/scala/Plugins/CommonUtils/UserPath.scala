package Plugins.CommonUtils

import Globals.GlobalVariables.serviceCode
import Plugins.CommonUtils.ServiceCenter.portMap

/** 这个是为了调试方便，给一些用户单独创建了运行的进程，这样的话就不会干扰到正常用户的进程（DefaultPath）。
 *  具体是在GlobalIOs.chosenPath里面定义的 */
class UserPath {
  val localAlgorithmRunner: Boolean = false
  def akkaServerHostName():String= "localhost"
  def seedNodeName():String= ServiceCenter.seedNodeName
  def dbServerName():String= ServiceCenter.dbServerName
  def dbPort():Int=5032
  def deploy():Boolean=true

  def mqHost : String = "rabbitmq"
  def mqPort : Int = 5672
  def mqUsername : String = "admin"
  def mqPassword : String = "password"
  /**
    * 设置 IP 地址和端口号，按照自下而上的顺序进行调试，调试成功注释掉该行,即可继续执行上一层调试，直至完成全部调试，
    * 成功实现部署。
    * @return (IP地址:端口号)
    */
  def getServerPort:String= ""
}
object UserPath{
  /** 选择某个Path运行程序 */
  var chosenPath:UserPath= new UserPath()
}
case class LocalTestPath() extends UserPath {
  override def dbServerName(): String = "localhost"
  override def dbPort(): Int = 5432
  override def akkaServerHostName(): String = "localhost"
  override def deploy(): Boolean = false
  override def seedNodeName():String = List(serviceCode).map(
    "\"akka://QianFangCluster@localhost:" + portMap(_) + "\""
  ).reduce(_ + "," + _)


  override def mqHost : String = "localhost"
  override def mqPort : Int = 5672
  override def mqUsername : String = "guest"
  override def mqPassword : String = "guest"
}
case class LocalAlgorithmPath() extends UserPath {
  override val localAlgorithmRunner= true
  override def dbServerName(): String = "localhost"
  override def dbPort(): Int = 5432
  override def akkaServerHostName(): String = "localhost"
  override def deploy(): Boolean = false
  override def seedNodeName():String = List(serviceCode).map(
    "\"akka://QianFangCluster@localhost:" + portMap(_) + "\""
  ).reduce(_ + "," + _)

  override def mqHost : String = "10.1.18.29"
  override def mqPort : Int = 30614
  override def mqUsername : String = "admin"
  override def mqPassword : String = "password"
}
