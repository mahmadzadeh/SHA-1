package com

import org.scalatest.FunSuite

class SHA1DigestTest extends FunSuite  {
    
    test("can create an instance") {

        val digest = new SHA1Digest(new Preprocessor)

        assertResult("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d") {
            digest.calculate("hello")
        }
    }

}
