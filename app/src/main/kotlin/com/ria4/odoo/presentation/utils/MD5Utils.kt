package com.ria4.odoo.presentation.utils

import androidx.annotation.NonNull
import java.security.MessageDigest

class MD5Utils {
    companion object {
        private val md5 = MessageDigest.getInstance("MD5")

        fun getMD5(@NonNull content: String): String? {
            md5.update(content.toByteArray())
//            val m = _md5.digest()// 加密
            return bytes2Hex(md5.digest())
        }

        fun hex2Bytes(hexStr: String?): ByteArray? {
            if (hexStr == null || hexStr.isEmpty())
                return null

            val result = ByteArray(hexStr.length / 2)
            for (i in 0 until hexStr.length / 2) {
                val high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16)
                val low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16)
                result[i] = (high * 16 + low).toByte()
            }
            return result
        }

        fun bytes2Hex(data: ByteArray?): String? {
            if (data == null || data.isEmpty())
                return null

            val buf = StringBuffer()
            for (i in data.indices) {
                if (data[i].toInt() and 0xff < 0x10) { /* & 0xff转换无符号整型 */
                    buf.append("0")
                }
                buf.append(java.lang.Long.toHexString((data[i].toInt() and 0xff).toLong())) /* 转换16进制,下方法同 */
            }
            return buf.toString()
        }
    }
}