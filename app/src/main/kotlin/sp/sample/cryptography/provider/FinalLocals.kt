package sp.sample.cryptography.provider

import android.content.Context
import sp.sample.cryptography.BuildConfig
import java.util.UUID

internal class FinalLocals(context: Context) : Locals {
    private val prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

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
}
