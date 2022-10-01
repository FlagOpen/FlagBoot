package Plugins.NucleicAPI.NucleicMessages

import Plugins.NucleicAPI.NucleicMessage

/**
 * 核酸试管初始化，返回是否初始化成功
 * @param tubeID
 */
case class TubeInitMessage(tubeID : String, cellphone : String, token : String) extends NucleicMessage[Boolean]
