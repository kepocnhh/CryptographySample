package sp.sample.cryptography.provider

import android.util.Base64
import sp.sample.cryptography.entity.SecretKeySpec
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

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

    override fun newSecretKey(): SecretKey {
        val generator = KeyGenerator.getInstance("AES")
        return generator.generateKey()
    }

    override fun toSecretKey(password: CharArray, spec: SecretKeySpec): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return factory.generateSecret(PBEKeySpec(password, spec.salt, spec.iterations, spec.length))
    }

    override fun base64(bytes: ByteArray): String {
        return String(Base64.decode(bytes, Base64.DEFAULT))
    }

    override fun base64(text: String): ByteArray {
        return Base64.encode(text.toByteArray(), Base64.DEFAULT)
    }
}
