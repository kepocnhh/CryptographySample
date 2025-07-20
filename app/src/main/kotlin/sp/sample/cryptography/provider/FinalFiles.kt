package sp.sample.cryptography.provider

import android.os.Environment
import sp.sample.cryptography.BuildConfig

internal class FinalFiles : Files {
    override val docs = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)!!.resolve(BuildConfig.APPLICATION_ID).also {
        it.mkdirs()
    }
}
