import java.security.MessageDigest
import kotlin.experimental.xor

class lab4c2 {
    class KeyMessage(val key: String, val message: String){}
    companion object {
        private val sizeOfBlock = 128
        private val sizeOfChar = 16
        private val shiftKey = 16
        private val quantityOfRounds = 16



        fun start() {


            val key = "mySecretKey".toByteArray()
            val encryptedMessage = code(key, "Hello, world!".toByteArray(), 10)
            val decryptedMessage = decode(key, encryptedMessage, 10)
            val message = String(decryptedMessage, Charsets.UTF_8)
            println(message) // Output: Hello, world!

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
                        val keyMessage = code(key.toByteArray(), message.toByteArray(), 8)
                        println()
                        println("message: " + keyMessage.decodeToString())
                        println("Расшифрование")

                        val result = decode(key.toByteArray(), keyMessage, 8)
                        println(result.decodeToString())
                    }

                    2 -> {
                        print("message: ")
                        val message = readLine()!!
                        print("key: ")
                        val key = readLine()!!
                        decode(key.toByteArray(), message.toByteArray(), 8)
                    }
                }
            }
        }

        fun code(key: ByteArray, message: ByteArray, numRounds: Int): ByteArray {
            val blockSize = key.size / 2
            val leftBlock = ByteArray(blockSize)
            val rightBlock = ByteArray(blockSize)
            System.arraycopy(key, 0, leftBlock, 0, blockSize)
            System.arraycopy(key, blockSize, rightBlock, 0, blockSize)

            for (i in 1..numRounds) {
                val tempBlock = leftBlock.clone()
                leftBlock.xor(getRoundKey(rightBlock, i))
                leftBlock.copyInto(rightBlock, 0, 0, blockSize)
                tempBlock.xor(leftBlock)
                leftBlock.copyInto(rightBlock, 0, 0, blockSize)
                tempBlock.copyInto(leftBlock, 0, 0, blockSize)
            }

            leftBlock.copyInto(rightBlock, 0, 0, blockSize)
            leftBlock.xor(rightBlock)
            return leftBlock + rightBlock
        }

        fun getRoundKey(key: ByteArray, roundNum: Int): ByteArray {
            val digest = MessageDigest.getInstance("SHA-256")
            var roundKey = key
            repeat(roundNum) {
                roundKey = digest.digest(roundKey)
            }
            return roundKey
        }

        fun decode(key: ByteArray, encryptedMessage: ByteArray, numRounds: Int): ByteArray {
            val blockSize = key.size / 2
            val leftBlock = ByteArray(blockSize)
            val rightBlock = ByteArray(blockSize)
            System.arraycopy(key, 0, leftBlock, 0, blockSize)
            System.arraycopy(key, blockSize, rightBlock, 0, blockSize)

            for (i in numRounds downTo 1) {
                val tempBlock = leftBlock.clone()
                leftBlock.xor(getRoundKey(rightBlock, i))
                leftBlock.copyInto(rightBlock, 0, 0, blockSize)
                tempBlock.xor(leftBlock)
                leftBlock.copyInto(rightBlock, 0, 0, blockSize)
                tempBlock.copyInto(leftBlock, 0, 0, blockSize)
            }

            leftBlock.copyInto(rightBlock, 0, 0, blockSize)
            leftBlock.xor(rightBlock)

            val decryptedMessage = ByteArray(encryptedMessage.size)
            System.arraycopy(leftBlock, 0, decryptedMessage, 0, blockSize)
            System.arraycopy(rightBlock, 0, decryptedMessage, blockSize, blockSize)
            return decryptedMessage
        }

    }
}
fun ByteArray.xor(other: ByteArray): ByteArray {
    val result = ByteArray(size)
    for (i in indices) {
        result[i] = (this[i] xor other[i % other.size])
    }
    return result
}