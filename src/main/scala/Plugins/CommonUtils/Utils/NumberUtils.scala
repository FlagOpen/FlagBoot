package Plugins.CommonUtils.Utils

import scala.math.BigDecimal.RoundingMode

object NumberUtils {

  val MinimumDoubleVal = 0.0000001

  def prettyRound(f:Double, precision:Int=4):Double= {
    val base=Math.pow(10,precision).toInt
    Math.round(f*base).toDouble/base
  }

  /** 保留小数点后 scale 位 */
  def prettyScale(f: Double, scale: Int = 4): Double = {
    BigDecimal(f).setScale(scale, RoundingMode.HALF_UP).toDouble
  }

  /** 由于浮点数存储机制,不能单纯用 == 判断是否相等, 会存在 x 位的 0.000001 但两数其实是相等的 */
  def doubleEquals(d1: Double, d2: Double): Boolean = {
    (d1 - d2).abs <= MinimumDoubleVal
  }

  def positiveDouble(d1: Double, d2: Double): Boolean = {
    d1 - d2 > MinimumDoubleVal
  }
}
