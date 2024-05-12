
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.X509EncodedKeySpec
import java.util.*

class lab7 {

    companion object {

        fun start() {
            val authentication = Authentication()
            authentication.main()
        }
    }

    private class Authentication{
        lateinit var bobKeySignature: KeyPair
        lateinit var bobPubKeyBlob: ByteArray

        fun main(){
            createKeys()

            val bobData = "Какие-то данные боба".toByteArray(StandardCharsets.UTF_8)
            println("Данные = ${String(bobData, Charsets.UTF_8)}")
            val bobSignature = createSignature(bobData, bobKeySignature.private)

            println("публичный ключ = ${Base64.getEncoder().encodeToString(bobKeySignature.public.encoded)}\n${bobKeySignature.public}")
            val privateKeyBytes: ByteArray = bobKeySignature.private.encoded
            val base64String: String = Base64.getEncoder().encodeToString(privateKeyBytes)
            val hexString: String = privateKeyBytes.joinToString("") { "%02x".format(it) }
            println("секретный ключ = $base64String")

            println("Цифровая подпись боба: ${Base64.getEncoder().encodeToString(bobSignature)}")

            if (verifySignature(bobData, bobSignature, bobPubKeyBlob)) {
                println("Подпись Боба была успешно проверена")
            }
        }

        private fun createKeys() {
            val keyPairGenerator = KeyPairGenerator.getInstance("EC")
            keyPairGenerator.initialize(256)
            bobKeySignature = keyPairGenerator.generateKeyPair()
            bobPubKeyBlob = bobKeySignature.public.encoded
        }

        private fun createSignature(data: ByteArray, key: PrivateKey): ByteArray {
            val signature: ByteArray
            val signingAlg = Signature.getInstance("SHA256withECDSA")
            signingAlg.initSign(key)
            signingAlg.update(data)
            signature = signingAlg.sign()
            return signature
        }

        private fun verifySignature(data: ByteArray, signature: ByteArray, pubKey: ByteArray): Boolean {
            var retValue = false
            val keyFactory = KeyFactory.getInstance("EC")
            val publicKeySpec = X509EncodedKeySpec(pubKey)
            val publicKey = keyFactory.generatePublic(publicKeySpec)
            val signingAlg = Signature.getInstance("SHA256withECDSA")
            signingAlg.initVerify(publicKey)
            signingAlg.update(data)
            retValue = signingAlg.verify(signature)
            return retValue
        }
    }
}