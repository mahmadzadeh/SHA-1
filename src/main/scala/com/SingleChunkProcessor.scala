package com

import java.nio.ByteBuffer

class SingleChunkProcessor(oneChunk: ByteBuffer) {
    require((oneChunk.limit() == 64))

    val extended = ByteBufferCopy.copyInto320ByteBuffer(oneChunk)


}
