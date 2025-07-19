package sp.sample.cryptography.entity

internal class SecretKeySpec(
    val salt: ByteArray,
    val iv: ByteArray,
    val iterations: Int,
    val length: Int,
)
