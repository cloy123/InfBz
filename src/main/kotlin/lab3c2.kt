import java.io.Console
import java.io.File
import java.nio.charset.Charset
import java.security.SecureRandom
import kotlin.experimental.xor

class lab3c2 {
    class KeyMessage(val key: String, val message: String){}
    companion object {

        fun start() {
            var command = -1
            while (command != 3) {
                println("Шифровать строку - 1, расшифровать - 2, выйти - 3")
                command = readLine()!!.toInt()
                when (command) {
                    1 -> {
                        print("message: ")
                        val message = readLine()!!
                        print("key: ")
                        val key = readLine()!!
                        val keyMessage = code(message, key)
                        println("newKey = " + keyMessage.key)
                        println("message = " + keyMessage.message)
                    }

                    2 -> {
                        val file = File("C:\\Desktop\\message.txt")
                        val file2 = File("C:\\Desktop\\key.txt")
                        val byteMessage = file.readBytes()
                        val byteKey = file2.readBytes()
                        println("message: ${byteMessage.decodeToString()}")
                        println("key: ${byteKey.decodeToString()}")
                        val decodeMessage = decode(byteMessage, byteKey)
                        println(decodeMessage)
                    }
                }
            }
        }

        private fun code(message: String, key: String): KeyMessage {

            val byteMessage = message.toByteArray()
            val byteKey = padKey(key.toByteArray(), byteMessage.size)

            val ciphertext = ByteArray(byteMessage.size)
            for(b in byteMessage){
                println(b)
            }
            for (i in byteMessage.indices) {
                ciphertext[i] = (byteMessage[i] xor byteKey[i])
            }
            val file = File("C:\\Desktop\\message.txt")
            val file2 = File("C:\\Desktop\\key.txt")
            file2.writeBytes(byteKey)
            file.writeBytes(ciphertext)

            return KeyMessage(byteKey.decodeToString(), ciphertext.decodeToString())
        }

        private fun decode(byteMessage: ByteArray, byteKey: ByteArray): String {
            val plaintext = ByteArray(byteMessage.size)
            for (i in byteMessage.indices) {
                plaintext[i] = (byteMessage[i] xor byteKey[i])
            }
            return plaintext.decodeToString()
        }

        private fun padKey(key: ByteArray, length: Int): ByteArray {
            if (key.size >= length) {
                return key
            }
            val paddedKey = key.copyOf(length)
            val random = SecureRandom()
            for (i in key.size until length) {
                paddedKey[i] = random.nextInt(256).toByte()
            }
            return paddedKey
        }
    }
}