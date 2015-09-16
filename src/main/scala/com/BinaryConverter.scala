package com

object BinaryConverter {

    def toBinrayWithPadding(byte: Byte): String = Integer.toBinaryString(byte).reverse.padTo(8,"0").reverse.mkString

    
}
