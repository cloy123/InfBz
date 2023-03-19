import java.math.BigInteger
import java.security.SecureRandom

class lab5 {
    companion object {

        fun start() {
            // Генерируем два случайных приватных ключа
            val privateA = generatePrivateKey()
            val privateB = generatePrivateKey()


            // Вычисляем открытые ключи для каждого участника
            val publicA = generatePublicKey(privateA)
            val publicB = generatePublicKey(privateB)

            // Обмен открытыми ключами
            val sharedSecretA = generateSharedSecret(privateA, publicB)
            val sharedSecretB = generateSharedSecret(privateB, publicA)

            // Проверка совпадения общих секретных ключей
            if (sharedSecretA == sharedSecretB) {
                println("Общий секретный ключ: ${sharedSecretA.toString(16)}")
            } else {
                println("Ошибка: общие секретные ключи не совпадают")
            }
        }

        // Генерация случайного приватного ключа
        private fun generatePrivateKey(): BigInteger {
            val random = SecureRandom()
            return BigInteger(128, random)
        }

        // Вычисление открытого ключа на основе приватного ключа
        private fun generatePublicKey(privateKey: BigInteger): BigInteger {
            val base = BigInteger("2")
            val modulus = (5454353454565465354).toBigInteger() * (5454353454565465354).toBigInteger()
            return base.modPow(privateKey, modulus)
        }

        // Генерация общего секретного ключа на основе приватного ключа и открытого ключа другого участника
        private fun generateSharedSecret(privateKey: BigInteger, publicKey: BigInteger): BigInteger {
            val modulus = (5454353454565465354).toBigInteger() * (5454353454565465354).toBigInteger()
            return publicKey.modPow(privateKey, modulus)
        }
    }
}