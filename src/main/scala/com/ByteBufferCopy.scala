package com

import java.nio.{ByteBuffer => JavaByteBuffer}
import scalax.io.nio.{ByteBuffer => ScalaByteBuffer}


object ByteBufferCopy {
    // from is 64 bytes which will be copied into a buffer of
    def copyInto320ByteBuffer(src: JavaByteBuffer) : JavaByteBuffer = JavaByteBuffer.allocate(320).put(src.array())

    def fillUpTo320Byte(buffer: JavaByteBuffer): Unit = {

        val byteBuffer = new ScalaByteBuffer(buffer)

        byteBuffer.zipWithIndex.foreach { i =>
            val index = i._2
            if(index >= 16 && index <= 79) {
                val a = buffer.getInt(index - 3 ) ^  buffer.getInt(index - 8 ) ^ buffer.getInt(index - 14 ) ^ buffer.getInt(index - 16 )

                buffer.putInt(index, a)
            }
        }
    }
}
