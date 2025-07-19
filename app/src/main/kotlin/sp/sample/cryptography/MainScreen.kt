package sp.sample.cryptography

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import sp.kx.bytes.toHEX

@Composable
internal fun MainScreen() {
    val insets = WindowInsets.systemBars.asPaddingValues()
    val encoded = remember { App.injection.assets.open("foo.public.der").use { it.readBytes() } }
    val public = remember { App.injection.secrets.toPublicKey(encoded) }
    val hash = remember { App.injection.secrets.sha256(public.encoded).toHEX() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets),
        ) {
            Spacer(Modifier.weight(1f))
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "public key:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = hash,
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
        }
    }
}
