package Plugins.CommonUtils


/** 保存所有的服务注册信息 */
object ServiceCenter {
  val serviceCode = "A000001"
  val userAccountServiceCode = "A000001"
  val treeObjectServiceCode = "A000002"
  val csPortalServiceCode = "A000003"
  val consiliaServiceCode = "A000004"
  val clinicPortalServiceCode = "A000005"
  val diagnosisOracleServiceCode = "A000006"
  val cloudSourcingServiceCode = "A000007"
  val ocrServiceCode = "A000008"
  val displayServiceCode = "A000009"
  val dashboardServiceCode = "A000010"
  val doctorServiceCode = "A000011"
  val propertyServiceCode = "A000012"
  val msAccountServiceCode = "A000013"
  val medicineRetailPriceServiceCode = "A000014"
  val incomeServiceCode = "A000015"
  val transactionServiceCode = "A000016"
  val paymentServiceCode = "A000017"
  val patientServiceCode = "A000018"
  val cashPoolServiceCode = "A000019"
  val versionUpdateServiceCode = "A000020"
  val gitlabControlServiceCode = "A000021"
  val clinicHubServiceCode = "A000022"
  val reconciliationServiceCode = "A000023"
  val oracleAlgorithmServiceCode = "A000024"
  val teaProductServiceCode = "A000025"
  val csPlatformServiceCode = "A000026"
  val bidServiceCode = "A000027"
  val wechatPortalServiceCode = "A000028"
  val departmentServiceCode = "A000029"
  val bankServiceCode = "A000030"
  val taskServiceCode = "A000031"
  val discountServiceCode = "A000032"
  val appPortalServiceCode = "A000033"
  val statisticsServiceCode = "A000034"
  val calendarServiceCode = "A000035"
  val performanceServiceCode = "A000036"
  val proposalServiceCode = "A000037"
  val desktopPortalServiceCode = "A000038"
  val appointmentServiceCode = "A000039"
  val followUpServiceCode = "A000040"

  /** ************************  算法  ************************* */
  val expertSystemServiceCode = "B000001"

  /** ************************  诊所系统微服务  ************************* */
  val clinicDeployServiceCode = "D000001"
  val clinicServerServiceCode = "D000002"
  val medicineManageServiceCode = "D000003"
  val roomInfoSyncServiceCode = "D000004"
  val fileUploadServiceCode = "D000005"
  val clinicDailyMiscServiceCode = "D000006"
  val printerServiceCode = "D000007"
  val idCardReaderServiceCode = "D000008"
  val clinicLogServiceCode = "D000009"
  val deployConfigBackendServiceCode = "D000010"
  /** ***************************************************************** */

  val fullNameMap: Map[String, String] = Map(
    userAccountServiceCode -> "用户账户（User-Account）",
    treeObjectServiceCode -> "知识引擎对象（Tree-Object）",
    csPortalServiceCode -> "众包门户（CS-Portal）",
    consiliaServiceCode -> "医案（Consilia）",
    clinicPortalServiceCode -> "诊所门户（Clinic-Portal）",
    diagnosisOracleServiceCode -> "诊断推荐（Diagnosis-Oracle）",
    cloudSourcingServiceCode -> "众包微服务（Cloud-Sourcing）",
    ocrServiceCode -> "OCR微服务（OCR）",
    displayServiceCode -> "成果展示（Display）",
    doctorServiceCode -> "医生管理（Doctor）",
    dashboardServiceCode -> "中台（Dashboard）",
    propertyServiceCode -> "知识引擎属性（Property）",
    msAccountServiceCode -> "微服务权限管理（MS-Account）",
    incomeServiceCode -> "收入微服务（Income）",
    medicineRetailPriceServiceCode -> "药物零售价（Medicine-Retail-Price）",
    transactionServiceCode -> "交易中枢（Transaction）",
    paymentServiceCode -> "付款接口（Payment）",
    patientServiceCode -> "患者微服务（Patient）",
    cashPoolServiceCode -> "资金池（Cash-Pool）",
    versionUpdateServiceCode -> "版本管理（Version-Update）",
    gitlabControlServiceCode -> "权限管理（GitLab-Control）",
    clinicHubServiceCode -> "诊所中心（Clinic-Hub）",
    reconciliationServiceCode -> "对账中心（Reconciliation）",
    oracleAlgorithmServiceCode -> "问诊算法（Oracle-Algorithm-Template）",
    teaProductServiceCode -> "茶饮制品（Tea-Product）",
    csPlatformServiceCode -> "众包平台信息（CS-Platform）",
    bidServiceCode -> "任务竞拍（Bid）",
    wechatPortalServiceCode -> "微信门户（Wechat-Portal）",
    departmentServiceCode -> "组织结构（Department）",
    bankServiceCode -> "薪酬管理（Bank）",
    taskServiceCode -> "同心任务（Task）",
    discountServiceCode -> "折扣管理（Discount）",
    appPortalServiceCode -> "小程序门户（App-Portal）",
    statisticsServiceCode -> "数据统计（Statistics）",
    calendarServiceCode -> "同心日历（Calendar）",
    performanceServiceCode -> "绩效管理（Performance）",
    proposalServiceCode -> "同心申报（Proposal）",
    desktopPortalServiceCode -> "同心门户（Desktop-Portal）",
    appointmentServiceCode -> "预约系统（Appointment）",
    followUpServiceCode -> "患者随访（Follow-Up）",

    /** ************************  算法  ************************* */
    expertSystemServiceCode -> "专家系统（Expert-System）",

    /** ************************  诊所系统微服务  ************************* */
    clinicDeployServiceCode -> "部署管理（Deploy）",
    clinicServerServiceCode -> "诊所中心（Clinic-Server）",
    medicineManageServiceCode -> "药品管理（Medicine-Manage）",
    roomInfoSyncServiceCode -> "医生患者信息同步（Room-Info-Sync）",
    fileUploadServiceCode -> "文件上传（File-Upload）",
    clinicDailyMiscServiceCode -> "诊所日常（Clinic-Daily-Misc）",
    printerServiceCode -> "打印机（Printer）",
    idCardReaderServiceCode -> "身份证读卡器（ID-Card-Reader）",
    clinicLogServiceCode -> "诊所日志（Clinic-Log）",
    deployConfigBackendServiceCode -> "部署管理设置（Deploy-Config-Backend）"

    /** ***************************************************************** */
  )

  val portalPortMap: Map[String, Int] = Map(
    desktopPortalServiceCode -> 6005
  )

  def portMap(serviceCode: String): Int = {
    serviceCode.drop(1).toInt +
      (if (serviceCode.head == 'A') 10000 else if (serviceCode.head == 'D') 20000 else 30000)
  }

  def serviceName(serviceCode: String): String = {
    val fullName = fullNameMap(serviceCode)
    val start = fullName.indexOf("（")
    val end = fullName.indexOf("）")
    fullNameMap(serviceCode).substring(start + 1, end).toLowerCase
  }

  def serverHostName: String = "localhost"

  def dbServerName: String = "postgres-service"

  def serverPort(serviceCode: String): String = serverHostName + ":" + portMap(serviceCode)

  def mainSchema: Option[String] = Some(serviceName(serviceCode).replace("-", "_"))

  val seedNodeName: String = List(serviceCode).map(
    "\"akka://QianFangCluster@" + serverPort(_) + "\""
  ).reduce(_ + "," + _)

  lazy val servicePort: Int = portMap(serviceCode)
  lazy val serviceFullName: String = fullNameMap(serviceCode)
}
