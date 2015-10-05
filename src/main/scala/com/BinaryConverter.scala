package com

import java.io.ByteArrayInputStream
import java.nio.charset.Charset
import scala.collection.mutable.Buffer

object BinaryConverter {

    def toByteArray(str: String) : Array[Byte] = str.getBytes(Charset.forName("UTF-8"))

    def toByteBuffer(str: String) : Buffer[Byte] = toByteArray(str).toBuffer

}

