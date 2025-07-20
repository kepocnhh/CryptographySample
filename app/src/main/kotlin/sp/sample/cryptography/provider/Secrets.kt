package sp.sample.cryptography.provider

import java.security.PublicKey
import java.security.spec.KeySpec
import javax.crypto.SecretKey

internal interface Secrets {
    fun toPublicKey(encoded: ByteArray): PublicKey
    fun toSecretKey(encoded: ByteArray): SecretKey
    fun getSecretKey(spec: KeySpec): SecretKey
    fun sha256(encoded: ByteArray): ByteArray
    fun encrypt(key: SecretKey, decrypted: ByteArray, iv: ByteArray): ByteArray
    fun decrypt(key: SecretKey, encrypted: ByteArray, iv: ByteArray): ByteArray
    fun encrypt(key: PublicKey, decrypted: ByteArray): ByteArray
}
