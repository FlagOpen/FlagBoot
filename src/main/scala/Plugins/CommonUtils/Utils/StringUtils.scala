package Plugins.CommonUtils.Utils

import org.joda.time.DateTime

import scala.collection.immutable.List

object StringUtils {
  /** 定义win/linux下面的slash */
  val slash: String = if (System.getProperty("os.name").startsWith("Windows")) "\\" else "/"
  val chars: Array[Char] = (('a' to 'z') ++ ('A' to 'Z')).toArray

  /** 产生一个length长度的随机数字串 */
  def randomNumber(length: Int): String =
    List.fill(length)(util.Random.nextInt(10)).mkString

  def nextLetter():Char= chars(util.Random.nextInt(52))

  /** 产生一个length长度的随机字母字符串 */
  def randomLetterString(length: Int): String =
    List.fill(length)(nextLetter()).mkString

  /** 编辑距离 */
  def levenshteinDistance(str1: String, str2: String): Int = {
    val xLen = str1.length
    val yLen = str2.length
    val dp = Array.fill(xLen + 1)(Array.fill(yLen + 1)(0))
    for (i <- Range(0, xLen + 1))
      for (j <- Range(0, yLen + 1))
        dp(i)(j) = i + j
    for (i <- Range(1, xLen + 1))
      for (j <- Range(1, yLen + 1)) {
        var d = 0
        if (str1(i - 1) == str2(j - 1))
          d = 0
        else
          d = 1
        dp(i)(j) = List(dp(i - 1)(j) + 1, dp(i)(j - 1) + 1, dp(i - 1)(j - 1) + d).min
      }
    dp(xLen)(yLen)
  }

  /** 转换成日期字符串 */
  def toDate(time: DateTime): String = time.toString("yyyy-MM-dd")

  /** 转换成日期字符串 */
  def toDate(time: Long): String = new DateTime(time).toString("yyyy-MM-dd")

  /** 转换成日期字符串 */
  def toDate(time: String): String = new DateTime(time).toString("yyyy-MM-dd")

}
