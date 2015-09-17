package com

import org.scalatest.FunSuite

class BinaryConverterTest extends FunSuite {

    test("given a byte then return binary representation of the Byte") {
        val bytes = "a".getBytes

        val expected = "01100001"

        assertResult(expected) {
            BinaryConverter.toBinaryWithPadding(bytes.head)
        }
    }

    test("given a multi-byte char then toBinrayWithPadding returns the correct binary representation") {
        val bytes = "ð".getBytes

        val expected = "11000011"

        assertResult(expected) {
            BinaryConverter.toBinaryWithPadding(bytes.head)
        }
    }

    test("given string then toBinary returns the correct binary representation of the string") {
        val string = "hello world"

        /**
         * http://string-functions.com/string-binary.aspx
         */
        val expected = "0110100001100101011011000110110001101111001000000111011101101111011100100110110001100100"

        assertResult(expected) {
            BinaryConverter.toBinary(string)
        }
    }
}
