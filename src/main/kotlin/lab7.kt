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
        lateinit var aliceKeySignature: KeyPair
        lateinit var alicePubKeyBlob: ByteArray

        fun main(){
            createKeys()

            val bobData = "Bob".toByteArray(StandardCharsets.UTF_8)
            val bobSignature = createSignature(bobData, aliceKeySignature.private)
            println("Bob created signature: ${Base64.getEncoder().encodeToString(bobSignature)}")

            if (verifySignature(bobData, bobSignature, alicePubKeyBlob)) {
                println("Подпись Боба была успешно проверена")
            }
        }

        private fun createKeys() {
            val keyPairGenerator = KeyPairGenerator.getInstance("EC")
            keyPairGenerator.initialize(256)
            aliceKeySignature = keyPairGenerator.generateKeyPair()
            alicePubKeyBlob = aliceKeySignature.public.encoded
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