package sp.sample.cryptography.provider

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import sp.kx.bytes.toByteArray
import sp.sample.cryptography.BuildConfig
import sp.sample.cryptography.entity.SecretKeySpec
import java.io.File
import java.util.UUID

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

    override val key: ByteArray
        get() {
            val fileName = "foo.key"
            val file = context.filesDir.resolve(fileName)
            if (!file.exists()) {
                file.encrypted().openFileOutput().use {
                    it.write(secrets.newSecretKey().encoded)
                }
            }
            return file.encrypted().openFileInput().use {
                it.readBytes()
            }
        }

    override val spec: SecretKeySpec
        get() {
            return SecretKeySpec(
                salt = 1752940070000L.toByteArray(), // todo
                iv = ByteArray(0),
                iterations = 10_000,
                length = 256,
            )
        }
}
