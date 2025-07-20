package sp.sample.cryptography.provider

import javax.crypto.spec.PBEKeySpec

internal interface Locals {
    val installId: String
    val spec: PBEKeySpec
    val iv: ByteArray
}
