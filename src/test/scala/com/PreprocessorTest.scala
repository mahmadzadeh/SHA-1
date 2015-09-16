package com

import org.scalatest.FunSuite

class PreprocessorTest extends FunSuite {

    test("can create instance") {
        val preprocessor = new Preprocessor
    }

    test("given an array of bits can pad it to right length for SHA-1") {
        val message = "anish"

        message.getBytes.foreach { oneByte =>
            println(Integer.toBinaryString(oneByte))
        }

        val preprocessor = new Preprocessor

        preprocessor.pad(message.getBytes)
    }
}
