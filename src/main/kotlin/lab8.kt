import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class lab8 {

    companion object {

        fun start() {
            val authentication = AES()
            authentication.main()
        }
    }


    private class AES {

        var secretKey = "MySecretKey" //должен быть 16, 24 или 32 байта
        var originalText = "какой-то текст!"
        var encryptedText = encryptAES(originalText, padKey(secretKey))
        var decryptedText = decryptAES(encryptedText, padKey(secretKey))
        var actions = "0 - конец," +
                "\n1 - поменять секретный ключ," +
                "\n2 - поменять исходный текст," +
                "\n3 - зашифровать текст," +
                "\n4 - расшифровать тест"

        fun main() {
            printCurrentState(secretKey, originalText, encryptedText, decryptedText)
            var i = 1
            while (true) {
                println("${i}--------------------------------------------------------------------------")
                printCurrentState(secretKey, originalText, encryptedText, decryptedText)
                println(actions)
                val action = readLine()
                if (action.isNullOrEmpty() || !"01234".contains(action)) {
                    continue
                }
                when (action.toInt()) {
                    0 -> break
                    1 -> secretKey = changeSecretKey()
                    2 -> originalText = changeOriginalText()
                    3 -> encryptedText = encryptAES(originalText, padKey(secretKey))
                    4 -> decryptedText = decryptAES(encryptedText, padKey(secretKey))
                }
                println("${i}--------------------------------------------------------------------------")
                i++
            }
        }

        fun padKey(secretKey: String): String {
            val targetKeyLength = 32 // Целевая длина ключа в байтах (32 байта для AES-256)
            val utf8Bytes = secretKey.toByteArray(Charsets.UTF_8)
            val paddedKeyBytes = ByteArray(targetKeyLength)
            for (i in 0 until targetKeyLength) {
                paddedKeyBytes[i] = if (i < utf8Bytes.size) utf8Bytes[i] else 0.toByte()
            }
            return String(paddedKeyBytes, Charsets.UTF_8)
        }

        private fun changeOriginalText(): String {
            var newText: String = ""
            while (newText.isEmpty()) {
                print("Новый исходный текст: ")
                newText = readLine().toString()
            }
            return newText
        }

        private fun changeSecretKey(): String {
            var newSecretKey: String = ""
            while (newSecretKey.isEmpty()) {
                print("Новый секретный ключ: ")
                newSecretKey = readLine().toString()
            }
            return newSecretKey
        }

        fun printCurrentState(secretKey: String, originalText: String, encryptedText: String, decryptedText: String) {
            println("Секретный ключ = $secretKey")
            println("Исходный текст = $originalText")
            println("Зашифрованный текст = $encryptedText")
            println("Расшифрованный текст = $decryptedText")
        }

        fun encryptAES(text: String, secretKey: String): String {
            val cipher =
                Cipher.getInstance("AES/CBC/PKCS5Padding") // Создаем экземпляр шифра с указанием алгоритма и режима работы
            val keySpec = SecretKeySpec(
                secretKey.toByteArray(),
                "AES"
            ) // Создаем спецификацию ключа с использованием секретного ключа
            val ivSpec =
                IvParameterSpec(secretKey.toByteArray().copyOf(16)) // Создаем спецификацию вектора инициализации (IV)
            cipher.init(
                Cipher.ENCRYPT_MODE,
                keySpec,
                ivSpec
            ) // Инициализируем шифр в режиме шифрования с указанием ключа и IV
            val encryptedBytes = cipher.doFinal(text.toByteArray()) // Шифруем текст в байтовом представлении
            return Base64.getEncoder().encodeToString(encryptedBytes) // Возвращаем зашифрованный текст в формате Base64
        }

        fun decryptAES(encryptedText: String, secretKey: String): String {
            val cipher =
                Cipher.getInstance("AES/CBC/PKCS5Padding") // Создаем экземпляр шифра с указанием алгоритма и режима работы
            val keySpec = SecretKeySpec(
                secretKey.toByteArray(),
                "AES"
            ) // Создаем спецификацию ключа с использованием секретного ключа
            val ivSpec =
                IvParameterSpec(secretKey.toByteArray().copyOf(16)) // Создаем спецификацию вектора инициализации (IV)
            cipher.init(
                Cipher.DECRYPT_MODE,
                keySpec,
                ivSpec
            ) // Инициализируем шифр в режиме дешифрования с указанием ключа и IV
            val decodedBytes =
                Base64.getDecoder().decode(encryptedText) // Декодируем зашифрованный текст из формата Base64
            val decryptedBytes = cipher.doFinal(decodedBytes) // Дешифруем текст в байтовом представлении
            return String(decryptedBytes) // Возвращаем дешифрованный текст в виде строки
        }
    }

}