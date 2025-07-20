package sp.sample.cryptography

import android.app.Application
import android.content.Context
import sp.sample.cryptography.provider.FinalAssets
import sp.sample.cryptography.provider.FinalFiles
import sp.sample.cryptography.provider.FinalLocals
import sp.sample.cryptography.provider.FinalSecrets
import sp.sample.cryptography.provider.Injection
import sp.sample.cryptography.provider.Secrets

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val context: Context = this
        val secrets: Secrets = FinalSecrets()
        _injection = Injection(
            locals = FinalLocals(context = context, secrets = secrets),
            assets = FinalAssets(context = context),
            secrets = secrets,
            files = FinalFiles(),
        )
    }

    companion object {
        private var _injection: Injection? = null
        val injection: Injection get() = checkNotNull(_injection) { "No injection!" }
    }
}
