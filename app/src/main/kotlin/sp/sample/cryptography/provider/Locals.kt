package sp.sample.cryptography.provider

import sp.sample.cryptography.entity.SecretKeySpec

internal interface Locals {
    val installId: String
    val key: ByteArray
    val spec: SecretKeySpec
}
