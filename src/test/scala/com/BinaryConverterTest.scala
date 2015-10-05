package com

import java.io.ByteArrayInputStream
import java.nio.charset.Charset

import com.BinaryConverter.{toByteArray, toByteBuffer}
import org.scalatest.FunSuite

class BinaryConverterTest extends FunSuite {

    val helloWorldInBinary = "0110100001100101011011000110110001101111001000000111011101101111011100100110110001100100"

    test("given a string of of byte then toByteArray returns array of bytes representing that string") {
        assertResult(1) {
            toByteArray("a").size
        }
    }

    test("given a multi-byte char then toByteArray returns array of bytes representing that string") {
        assertResult(2) {
            toByteArray("ð").size
        }
    }

    test("given a multi-byte char then toByteBuffer returns buffer of bytes representing that string") {
        val mutilByteString= "чытрш"

        assert(toByteBuffer(mutilByteString).size > mutilByteString.length)
    }
}
