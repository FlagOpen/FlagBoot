package Plugins.NucleicAPI.NucleicMessages

import Plugins.NucleicAPI.NucleicMessage

/**
 * 核酸试管结果上传，返回操作是否成功
 * @param tubeID
 * @param positive
 */
case class TubeFinishMessage(tubeID : String, positive : Boolean, cellphone : String, token : String) extends NucleicMessage[Boolean]
