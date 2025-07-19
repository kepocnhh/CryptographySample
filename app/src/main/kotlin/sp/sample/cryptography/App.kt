package sp.sample.cryptography

import android.app.Application
import android.content.Context
import sp.sample.cryptography.provider.FinalLocals
import sp.sample.cryptography.provider.Injection

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val context: Context = this
        _injection = Injection(
            locals = FinalLocals(context = context),
        )
    }

    companion object {
        private var _injection: Injection? = null
        val injection: Injection get() = checkNotNull(_injection) { "No injection!" }
    }
}
