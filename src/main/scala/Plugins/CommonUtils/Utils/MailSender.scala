package Plugins.CommonUtils.Utils

import Plugins.CommonUtils.ServiceCenter.serviceFullName
import Plugins.CommonUtils.UserPath.chosenPath

import java.util.Properties
import javax.activation.{CommandMap, DataHandler, FileDataSource, MailcapCommandMap}
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import javax.mail._
import scala.util.Try

/** 发送邮件 */
object MailSender {
  private var emailProperties: Properties = _
  private var mailSession: Session = _
  private var emailMessage: MimeMessage = _
  private val userName = "309459245@qq.com"
  private val password = "ympqvlzymtdecbdc"

  val mc: MailcapCommandMap = CommandMap.getDefaultCommandMap.asInstanceOf[MailcapCommandMap]
  mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html")
  mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml")
  mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain")
  mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed")
  mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822")
  CommandMap.setDefaultCommandMap(mc)

  def sendEmail(toEmails: Array[String], body: String = "123456", title: String = ""): Try[Unit] = Try {
    emailProperties = System.getProperties //获取环境变量
    emailProperties.setProperty("mail.smtp.host", "smtp.qq.com")
    emailProperties.put("mail.smtp.auth", "true")
    mailSession = Session.getDefaultInstance(emailProperties, new Authenticator() {
      override def getPasswordAuthentication = new PasswordAuthentication(userName, password) //发件人邮件用户名、授权码
    })
    emailMessage = new MimeMessage(mailSession)
    for (target <- toEmails)
      emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(target))
    emailMessage.setFrom(new InternetAddress(userName))
    emailMessage.setSubject(title)
    emailMessage.setText(body) //for a html email
    Transport.send(emailMessage)
  }

  /** 把exception发送给slack */
  def emailException(e: Throwable): Unit = {
    e.printStackTrace()
    if (chosenPath.deploy())
      MailSender.sendEmail(Array("e1f7t7l0f6a0z6z4@th-tcm.slack.com"),
        IOUtils.exceptionToString(e), s"${serviceFullName}的新错误！")
  }

  /** 把警告发送给slack */
  def emailWarning(e: Throwable): Unit = {
    e.printStackTrace()
    if (chosenPath.deploy())
      MailSender.sendEmail(Array("d2r4p2d7g3d1m4s8@th-tcm.slack.com"),
        IOUtils.exceptionToString(e), s"${serviceFullName}的新警告！")
  }

  def emailFile(toEmails: Array[String], fileName: String, emailTitle: String = "结算"): Unit = {

    emailProperties = System.getProperties //获取环境变量
    emailProperties.setProperty("mail.smtp.host", "smtp.qq.com")
    emailProperties.put("mail.smtp.auth", "true")
    mailSession = Session.getDefaultInstance(emailProperties, new Authenticator() {
      override def getPasswordAuthentication = new PasswordAuthentication(userName, password) //发件人邮件用户名、授权码
    })
    emailMessage = new MimeMessage(mailSession)
    for (target <- toEmails)
      emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(target))
    emailMessage.setFrom(new InternetAddress(userName))
    emailMessage.setSubject(emailTitle)

    // Create the message part
    var messageBodyPart = new MimeBodyPart()
    // Now set the actual message
    messageBodyPart.setText("This is message body")

    // Create a multipar message
    val multipart = new MimeMultipart()

    // Set text message part
    multipart.addBodyPart(messageBodyPart)

    // Part two is attachment
    messageBodyPart = new MimeBodyPart()
    val source = new FileDataSource(fileName)
    messageBodyPart.setDataHandler(new DataHandler(source))
    messageBodyPart.setFileName(fileName)
    multipart.addBodyPart(messageBodyPart)

    // Send the complete message parts
    emailMessage.setContent(multipart)
    Transport.send(emailMessage)
  }
}
