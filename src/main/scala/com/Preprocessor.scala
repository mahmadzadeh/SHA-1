package com

import scala.annotation.tailrec
import scala.collection.mutable.Buffer


class Preprocessor {
    def pad(bytes: Buffer[Byte]): Buffer[Byte] = {

        @tailrec
        def zeroPaddedToMultipleOf64Bytes(bytes: Buffer[Byte]): Buffer[Byte] = {
            if ((bytes.length + 8) % 64 == 0) {
                bytes;
            } else {
                zeroPaddedToMultipleOf64Bytes(bytes += 0x0)
            }
        }

        zeroPaddedToMultipleOf64Bytes(bytes += 0x1 )
    }
}
