package sp.sample.cryptography.provider

import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

internal class FinalSecrets : Secrets {
    override fun toPublicKey(encoded: ByteArray): PublicKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(encoded)
        return keyFactory.generatePublic(keySpec)
    }

    override fun sha256(encoded: ByteArray): ByteArray {
        val md = MessageDigest.getInstance("SHA256")
        return md.digest(encoded)
    }
}
