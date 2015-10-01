package com

import java.io.ByteArrayInputStream
import java.nio.charset.Charset

object BinaryConverter {

    def toByte(c: Char): Byte = c.toByte

    def toBinarySeq(stream: ByteArrayInputStream): Seq[Byte] =
        scala.io.Source.fromInputStream(stream, "UTF-8").collect {
            case b => toByte(b)
        }.toSeq

    def toByteArray(str: String) : Array[Byte] = str.getBytes(Charset.forName("UTF-8"))

}

