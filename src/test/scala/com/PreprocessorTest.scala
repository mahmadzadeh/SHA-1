import com.Preprocessor

import scala.collection.mutable

import java.nio.charset.Charset

import org.scalatest.FunSuite

import scala.collection.mutable.Buffer

class PreprocessorTest extends FunSuite {

    val preprocessor= new Preprocessor()

    val messageSizeInBytes = 8

    test("given message m of size different length then pad returns padded msg to the correct length") {

        val lengths = List(0, 1, 64 , 512)

        lengths.foreach { len =>

            val paddedMeg = preprocessor.pad(bytesBufferForMessage(createStringOfLength(len, "")))

            assertPaddedMsgSize(paddedMeg)
        }
    }

    // msg "a" (97) 01100001
    // one          00000001
    // msg + one  = 01100001 00000001 + [enough padding to be a multiple of 64 bytes]
    test("given binary message m of size 1 byte then pad returns always adds a 1 to the end of msg before padding start") {
        val message = "a"

        val b = bytesBufferForMessage(message)

        val paddedMsg = preprocessor.pad(b)

        assertPaddedMsgSize(paddedMsg)

        assert(paddedMsg.tail.head == 1)
    }

    test("given message m of same size as Hamlet in bytes then the message is padded correctly") {
        val b = bytesBufferForMessage( createStringOfLength(148850, "0") )

        val paddedMsg = preprocessor.pad(b)

        assertPaddedMsgSize(paddedMsg)
    }

    test("given message m of length L then appendMsgLengthToPaddedMsg adds the message length to the end of byte array") {
        val b = bytesBufferForMessage( createStringOfLength(1, "0") )
        val msgLenLong = b.length.toLong
        val paddedMsg = preprocessor.pad(b)

        assertPaddedMsgSize(paddedMsg)

        val paddedMessageWithLength= preprocessor.appendMessageLengthToPaddedMessage( msgLenLong, paddedMsg)

        assertPaddedMsgLength(paddedMessageWithLength, msgLenLong)
    }

    test("given a single character as message then countOf64ByteChunks returns 1 ") {
        val b = bytesBufferForMessage( createStringOfLength(1, "0") )
        val msgLenLong = b.length.toLong

        val paddedMessageWithLength= preprocessor.appendMessageLengthToPaddedMessage( msgLenLong, preprocessor.pad(b))

        assertResult(1) {
            preprocessor.countOf64ByteChunks(paddedMessageWithLength)
        }
    }

    // msg : 0......0 (56 bytes)
    // msg: msg + 1 = 0 .....0 1 (57 bytes)
    // msg + padding + 8 bytes (size ) = 128 bytes
    test("given a 56 character as message then countOf64ByteChunks returns 1") {
        val b = bytesBufferForMessage( createStringOfLength(56, "0") )
        val msgLenLong = b.length.toLong

        val padded = preprocessor.pad(b)

        val paddedMessage = preprocessor.appendMessageLengthToPaddedMessage(msgLenLong, padded)

        val paddedMessageWithLength= paddedMessage

        assertResult(2) {
            preprocessor.countOf64ByteChunks(paddedMessageWithLength)
        }
    }

    def assertPaddedMsgSize(paddedMsg: mutable.Buffer[Byte]): Unit = {
        assertResult(0) {
            (paddedMsg.size + messageSizeInBytes) % 64
        }
    }

    def assertPaddedMsgLength(paddedMsg: mutable.Buffer[Byte] , msgLgn: Long): Unit = {
        assertResult(0) {
            (paddedMsg.size) % 64
        }
    }

    private def createStringOfLength(len:Int, symbol: String ): String = (symbol * len)


    def bytesBufferForMessage(message: String): Buffer[Byte] = {
        message.getBytes(Charset.forName("UTF-8")).toBuffer[Byte]
    }

}
