package sp.sample.cryptography.provider

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import sp.kx.bytes.toByteArray
import sp.sample.cryptography.BuildConfig
import java.io.File
import java.util.UUID
import javax.crypto.spec.PBEKeySpec

internal class FinalLocals(
    private val context: Context,
    private val secrets: Secrets,
) : Locals {
    private val prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    private val prefsEncrypted = EncryptedSharedPreferences.create(
        "${BuildConfig.APPLICATION_ID}:encrypted",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private fun File.encrypted(): EncryptedFile {
        return EncryptedFile.Builder(
            this,
            context,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
        ).build()
    }

    override val installId: String
        get() {
            val id = prefs.getString("installId", null)
            if (id == null) {
                val installId = UUID.randomUUID().toString()
                prefs.edit()
                    .putString("installId", installId)
                    .commit()
                return installId
            } else {
                return id
            }
        }

    override val spec: PBEKeySpec
        get() {
            val password = UUID.fromString("dc301e15-5d68-4325-9bb5-b0fa8d96ae5e").toString()
            val salt = 1752940070000L.toByteArray()
            val iterations = 10_000
            val length = 256
            return PBEKeySpec(password.toCharArray(), salt, iterations, length)
        }

    override val iv = UUID.fromString("1e304f22-03d0-4445-9722-bfd88bf6078b").toByteArray()
}
