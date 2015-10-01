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

    def assertPaddedMsgSize(paddedMsg: mutable.Buffer[Byte]): Unit = {
        assertResult(0) {
            (paddedMsg.size + messageSizeInBytes) % 64
        }
    }

    private def createStringOfLength(len:Int, symbol: String ): String = (symbol * len)


    def bytesBufferForMessage(message: String): Buffer[Byte] = {
        message.getBytes(Charset.forName("UTF-8")).toBuffer[Byte]
    }

}
