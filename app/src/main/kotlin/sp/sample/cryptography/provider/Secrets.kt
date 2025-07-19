package sp.sample.cryptography.provider

import java.security.PublicKey

internal interface Secrets {
    fun toPublicKey(encoded: ByteArray): PublicKey
    fun sha256(encoded: ByteArray): ByteArray
}
