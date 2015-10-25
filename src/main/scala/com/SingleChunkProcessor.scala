package com

import java.nio.ByteBuffer

class SingleChunkProcessor {

    def chunkProcessor(oneChunk: ByteBuffer, sha1Variables: SHA1Variables) : SHA1Variables = {
        require((oneChunk.limit() == 64))

        val extended = SHA1Utility.fillUpTo320Bytes(SHA1Utility.copyInto320ByteBuffer(oneChunk))

        return SHA1Utility.sha1MainLoop(extended, sha1Variables)
    }
}
