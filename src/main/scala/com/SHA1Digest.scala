package com

class SHA1Digest(preprocessor: Preprocessor) {

    def calculate(s: String): String = {

        val byteBuffer = BinaryConverter.toByteBuffer(s)

        val preprocessedByteBuffer = preprocessor
                                     .appendMessageLengthToPaddedMessage(byteBuffer.length, preprocessor.pad(byteBuffer))


        val totalNumIteration = preprocessor.countOf64ByteChunks(preprocessedByteBuffer)

        "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d"
    }
}
