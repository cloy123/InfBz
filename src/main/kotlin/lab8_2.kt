import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.Signature

class lab8_2 {
    companion object {

        fun start() {
            val authentication = CryptographicServices()
            authentication.main()
        }
    }

    private class CryptographicServices{
        fun main(){

            //Вычисление хеша данных:
            val alg = MessageDigest.getInstance("SHA-256")
            val data = "какой-то текст для проверки".toByteArray(StandardCharsets.US_ASCII)
            val hash = alg.digest(data)

            //Генерация ключевой пары RSA:
            val rsa = java.security.KeyPairGenerator.getInstance("RSA").generateKeyPair()
            val publicKey = rsa.public
            val privateKey = rsa.private

            //Создание подписи для хеша данных:
            val rsaSignature = Signature.getInstance("SHA256withRSA")
            rsaSignature.initSign(privateKey)
            rsaSignature.update(hash)
            val signedHash = rsaSignature.sign()

            //Проверка подписи с использованием публичного ключа RSA:
            val rsaVerifier = Signature.getInstance("SHA256withRSA")
            rsaVerifier.initVerify(publicKey)
            rsaVerifier.update(hash)

            if (rsaVerifier.verify(signedHash)) {
                println("Подпись действительна")
            } else {
                println("Подпись не действительна")
            }
        }
    }
}


