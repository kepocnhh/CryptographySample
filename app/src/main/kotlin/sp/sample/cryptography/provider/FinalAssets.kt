package sp.sample.cryptography.provider

import android.content.Context
import java.io.InputStream

internal class FinalAssets(context: Context) : Assets {
    private val manager = context.assets

    override fun open(name: String): InputStream {
        return manager.open(name)
    }
}
