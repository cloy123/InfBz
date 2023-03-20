import java.io.File
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.SecureRandom

class lab6 {
    companion object {

        fun start() {//kakoye-to soobshcheniye
            var command = -1
            val rsa = RSA()
            println("publicKey = " + rsa.publicKey)
            println("privateKey = " + rsa.privateKey)
            while (command != 3) {
                println("Шифровать строку - 1, расшифровать - 2, выйти - 3")
                command = readLine()!!.toInt()
                when (command) {
                    1 -> {
                        print("message: ")
                        val message = readLine()!!
                        val result = rsa.code(message)
                        println(result)
                    }

                    2 -> {
                        print("message: ")
                        val message = readLine()!!
                        println(rsa.decode(message.toBigInteger()))
                    }
                }
            }
        }
    }

    private class RSA {
        private val random = SecureRandom()
        var privateKey: BigInteger
        var publicKey: BigInteger
        private var n: BigInteger

        init {
            val p = BigInteger.probablePrime(128, random)
            val q = BigInteger.probablePrime(128, random)
            val phi = (p - BigInteger.ONE) * (q - BigInteger.ONE)
            n = p * q
            publicKey = BigInteger("65537")//BigInteger("2")
            while (publicKey < phi){
                if(gcd(publicKey, phi) == BigInteger.ONE){
                    break;
                }else{
                    publicKey += BigInteger.ONE
                }
            }
            //val k = BigInteger("2")
            //privateKey = (BigInteger.ONE + (k * phi)/publicKey)
            privateKey = publicKey.modInverse(phi)
        }

        fun code(message: String): BigInteger {
            val messageInt = BigInteger(message.toByteArray(StandardCharsets.UTF_8))
            return messageInt.modPow(publicKey, n)
        }

        fun decode(ciphertext: BigInteger): String {
            val messageInt = ciphertext.modPow(privateKey, n)
            val messageBytes = messageInt.toByteArray()
            return String(messageBytes, StandardCharsets.UTF_8)
        }
        fun gcd(a: BigInteger, h: BigInteger): BigInteger {
            // Нахожение НОД
            var a = a
            var h = h
            var temp: BigInteger
            while (true) {
                temp = a.mod(h)
                if (temp == BigInteger.ZERO) return h
                a = h
                h = temp
            }
        }
    }
}