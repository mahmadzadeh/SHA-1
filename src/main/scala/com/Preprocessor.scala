package com

import java.io.{DataOutputStream, ByteArrayOutputStream}

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.Buffer


class Preprocessor {
    def pad(bytes: Buffer[Byte]): Buffer[Byte] = {

        @tailrec
        def zeroPaddedToMultipleOf64Bytes(bytes: Buffer[Byte]): Buffer[Byte] = {
            if ((bytes.length + 8) % 64 == 0)
                bytes;
            else
                zeroPaddedToMultipleOf64Bytes(bytes += 0x0)
        }

        zeroPaddedToMultipleOf64Bytes(bytes += 0x1 )
    }

    def appendMessageLengthToPaddedMessage( msgLeng: Long , paddedMsg: Buffer[Byte]) : Buffer[Byte] = paddedMsg ++ getBytes(msgLeng)

    private def getBytes(long: Long): Buffer[Byte] = {
        val byteArrayOutputStream = new ByteArrayOutputStream()
        val os = new DataOutputStream(byteArrayOutputStream)
        os.writeLong(long)

        os.close()

        byteArrayOutputStream.toByteArray.toBuffer
    }

    def countOf64ByteChunks(bytes: mutable.Buffer[Byte]): Long  = bytes.length / 64
}
