package sp.sample.cryptography.provider

import sp.sample.cryptography.entity.SecretKeySpec
import java.security.PublicKey
import javax.crypto.SecretKey

internal interface Secrets {
    fun toPublicKey(encoded: ByteArray): PublicKey
    fun sha256(encoded: ByteArray): ByteArray
    fun newSecretKey(): SecretKey
    fun toSecretKey(password: CharArray, spec: SecretKeySpec): SecretKey
    fun base64(bytes: ByteArray): String
    fun base64(text: String): ByteArray
}
