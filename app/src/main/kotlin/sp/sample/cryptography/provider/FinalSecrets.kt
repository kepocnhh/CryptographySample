package sp.sample.cryptography.provider

import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PublicKey
import java.security.spec.KeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

internal class FinalSecrets : Secrets {
    override fun toPublicKey(encoded: ByteArray): PublicKey {
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(encoded)
        return keyFactory.generatePublic(keySpec)
    }

    override fun toSecretKey(encoded: ByteArray): SecretKey {
        return SecretKeySpec(encoded, "AES")
    }

    override fun getSecretKey(spec: KeySpec): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return factory.generateSecret(spec)
    }

    override fun sha256(encoded: ByteArray): ByteArray {
        val md = MessageDigest.getInstance("SHA256")
        return md.digest(encoded)
    }

    override fun encrypt(key: SecretKey, decrypted: ByteArray, iv: ByteArray): ByteArray {
//        val cipher = Cipher.getInstance("AES")
//        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
//        val cipher = Cipher.getInstance("AES_256/CBC/NOPADDING")
//        val cipher = Cipher.getInstance("AES_256/CBC/PKCS5PADDING")
//        val cipher = Cipher.getInstance("AES/GCM/NOPADDING")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
//        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
        return cipher.doFinal(decrypted)
    }

    override fun decrypt(key: SecretKey, encrypted: ByteArray, iv: ByteArray): ByteArray {
//        val cipher = Cipher.getInstance("AES")
//        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
//        val cipher = Cipher.getInstance("AES/GCM/NOPADDING")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, key)
//        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        return cipher.doFinal(encrypted)
    }

    override fun encrypt(key: PublicKey, decrypted: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(decrypted)
    }
}
