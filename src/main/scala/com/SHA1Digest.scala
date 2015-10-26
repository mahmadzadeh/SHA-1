package com

import java.lang.Integer.rotateLeft
import java.nio.ByteBuffer

class SHA1Digest(preprocessor: Preprocessor) {

    def calculate(s: String): String = {

        val byteBuffer = BinaryConverter.toByteBuffer(s)

        val preprocessedByteBuffer = preprocessor
                                     .appendMessageLengthToPaddedMessage(byteBuffer.length, preprocessor.pad(byteBuffer))

        val totalNumIteration = preprocessor.countOf64ByteChunks(preprocessedByteBuffer)

        val buffer  = ByteBuffer
                      .allocate(preprocessedByteBuffer.length)
                      .put(preprocessedByteBuffer.toArray)

        var sha1Variables = SHA1Variables()

        val singleChunkProcessor = new SingleChunkProcessor

        (0.until(totalNumIteration)).map { it =>
            val chunk = getChunkFromByteBuffer(it, buffer)

            sha1Variables  = singleChunkProcessor.chunkProcessor(chunk, sha1Variables)
        }

        val hh = rotateLeft(sha1Variables.h0, 128) |
                 rotateLeft(sha1Variables.h1, 96) |
                 rotateLeft(sha1Variables.h2, 64) |
                 rotateLeft(sha1Variables.h3, 32) |
                 sha1Variables.h4

       ""
    }

    def getChunkFromByteBuffer(index: Int, bytes: ByteBuffer): ByteBuffer= {
        bytes.flip()

        val dest = Array.fill[Byte](64)(0)

        bytes.get(dest, index * 64, 64 )

        bytes.flip()

        ByteBuffer.allocate(64).put(dest)
    }
}
