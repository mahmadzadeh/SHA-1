package com

import java.lang.Integer.rotateLeft
import java.nio.{ByteBuffer => JavaByteBuffer}

import scalax.io.nio.{ByteBuffer => ScalaByteBuffer}


object SHA1Utility {
    
    // from is 64 bytes which will be copied into a buffer of size 320 bytes
    def copyInto320ByteBuffer(src: JavaByteBuffer) : JavaByteBuffer = JavaByteBuffer.allocate(320).put(src.array())

    def fillUpTo320Bytes(buffer: JavaByteBuffer): JavaByteBuffer = {
        require(buffer.limit() == 320)

        val byteBuffer = new ScalaByteBuffer(buffer)

        byteBuffer.zipWithIndex.foreach { i =>
            val index = (i._2)*4

            if(index >= 64 && index <= 316) {
                val a = XORAndRotateLeftByOne(
                        buffer.getInt(index - (3 * 4)) ,
                        buffer.getInt(index - (8 * 4)) ,
                        buffer.getInt(index - (14 * 4)) ,
                        buffer.getInt(index - (16 * 4)))

                buffer.putInt(index, a )
            }
        }

        buffer
    }

    def sha1MainLoop(buffer: JavaByteBuffer , sha1Vars: SHA1Variables): SHA1Variables = {
        
        var a = sha1Vars.h0
        var b = sha1Vars.h1
        var c = sha1Vars.h2
        var d = sha1Vars.h3
        var e = sha1Vars.h4

        var f: Int = 0
        var k: Int = 0
        var temp: Int = 0

        val byteBuffer = new ScalaByteBuffer(buffer)

        (0 to 79).foreach { index =>
            val fourByteIndex = index * 4

            index match {
                case in if 0 <= in && in <=19 =>
                    f = (b & c ) | (~b & d)
                    k = 0x5A827999
                case in if 20 <= in && in <=39 =>
                    f = b ^ c ^ d
                    k = 0x6ED9EBA1
                case in if 40 <= in && in <=59 =>
                    f = (b & c ) | (b & d ) | (c & d )
                    k = 0x8F1BBCDC
                case in if 60 <= in && in <=79 =>
                    f = b ^ c ^ d
                    k = 0xCA62C1D6
            }

            temp  = (rotateLeft(a,5)) + f + e + k + buffer.getInt(fourByteIndex)
            e = d
            d = c
            c = rotateLeft(b, 30)
            b = a
            a = temp
        }

        new SHA1Variables(sha1Vars.h0 + a, sha1Vars.h1 + b , sha1Vars.h2 + c , sha1Vars.h3 + d , sha1Vars.h4 + e )
    }

    def XORAndRotateLeftByOne(a: Int, b: Int, c:Int , d:Int ): Int = rotateLeft( a ^ b ^ c ^ d, 1)
}


