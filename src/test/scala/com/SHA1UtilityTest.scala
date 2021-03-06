
package com

import org.scalatest.FunSuite
import java.nio.{ByteBuffer => JavaByteBuffer}
import scalax.io.nio.{ByteBuffer => ScalaByteBuffer}


class SHA1UtilityTest extends FunSuite {


    test("given from ByteBuffer then Copy() will copy from source into destination") {
        val sizeInBytes: Int = 64

        val source: JavaByteBuffer = getByteBufferOfSize(sizeInBytes)

        val result = SHA1Utility.copyInto320ByteBuffer(source)

        assertByteBufferContent(source, result)

        assertResult( 320 ) {result.capacity()}
    }

    test("given from ByteBuffer then fillUpTo320Bytes() will fill the rest of the buffer up to 320th byte") {
        val sizeInBytes: Int = 64

        val source = getByteBufferOfSize(sizeInBytes)

        val result2 = SHA1Utility.fillUpTo320Bytes(SHA1Utility.copyInto320ByteBuffer(source))

        assertResult(320) {
            result2.position()
        }

        assertResult(320) {
            result2.limit()
        }
    }

    def assertByteBufferContent(b1:JavaByteBuffer, b2: JavaByteBuffer): Unit = {

        b1.flip()
        b2.flip()

        val bb1 = new ScalaByteBuffer(b1)

        bb1.zipWithIndex.foreach { byteIndexTuple =>
            assert(b2.get() == byteIndexTuple._1)
        }

        b1.flip()
        b2.flip()

        b1.rewind()
        b2.rewind()
    }


    def getByteBufferOfSize(sizeInBytes: Int): JavaByteBuffer = {
        val byteArray = ("1" * sizeInBytes).getBytes("UTF-8")

        assertResult(sizeInBytes) {
            byteArray.size
        }

        val source = JavaByteBuffer.allocate(sizeInBytes).put(byteArray)

        assertResult(sizeInBytes) {
            source.limit()
        }
        source
    }
}
