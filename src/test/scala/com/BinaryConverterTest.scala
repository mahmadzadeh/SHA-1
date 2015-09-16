package com

import org.scalatest.FunSuite

class BinaryConverterTest extends FunSuite  {

    test("given a byte then return binray resresentaion of the Byte") {
        val bytes = "a".getBytes

        println(bytes.size)

        val expected = "01100001"

        assertResult(expected) {
            BinaryConverter.toBinrayWithPadding(bytes(0))
        }
    }

    test("given a byte > then return binray resresentaion of the Byte") {
        val bytes = "ð".getBytes

        val expected = "11000011"

        assertResult(expected) {
                BinaryConverter.toBinrayWithPadding(bytes(0))
        }
    }

}
