package Plugins.NucleicAPI.NucleicMessages

import Plugins.NucleicAPI.NucleicMessage

/**
 * 核酸试管添加用户信息，返回操作是否成功
 * @param tubeID
 * @param cellphone
 */
case class TubeAddUserMessage(tubeID : String, cellphone : String, token : String) extends NucleicMessage[Boolean]
