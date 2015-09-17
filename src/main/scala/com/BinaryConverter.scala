package com

object BinaryConverter {
    
    def toBinary(s: String): String  = s.getBytes().map( toBinaryWithPadding ).mkString

    def toBinaryWithPadding(byte: Byte): String = Integer.toBinaryString(byte & 0xFF).reverse.padTo(8,"0").reverse.mkString


    
}
