package com

import scala.collection.mutable

class SHA1Digest(preprocessor: Preprocessor) {

    def calculate(s: String): String = {

        val byteBuffer = BinaryConverter.toByteBuffer(s)

        val preprocessedByteBuffer = preprocessor
                                     .appendMessageLengthToPaddedMessage(byteBuffer.length, preprocessor.pad(byteBuffer))


        val totalNumIteration = preprocessor.countOf64ByteChunks(preprocessedByteBuffer)

        (0L.to(totalNumIteration)).map { it =>
            val chunk = getChunkFromByteBuffer(it, preprocessedByteBuffer)
        }

        "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d"
    }

    def getChunkFromByteBuffer(index: Long, bytes: mutable.Buffer[Byte]): mutable.Buffer[Byte]= {
//        val start = index *
        null
    }
}
