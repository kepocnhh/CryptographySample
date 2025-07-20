package sp.sample.cryptography

import android.content.Intent
import android.media.MediaScannerConnection
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import sp.kx.bytes.toHEX

@Composable
internal fun MainScreen() {
    val context = LocalContext.current
    val insets = WindowInsets.systemBars.asPaddingValues()
    val installId = remember { App.injection.locals.installId }
    val publicKeyEncoded = remember { App.injection.assets.open("foo.public.der").use { it.readBytes() } }
    val publicKey = remember { App.injection.secrets.toPublicKey(publicKeyEncoded) }
    val publicKeyHash = remember { App.injection.secrets.sha256(publicKey.encoded).toHEX() }
    val spec = remember { App.injection.locals.spec }
    val iv = remember { App.injection.locals.iv }
    val secretKey = remember { App.injection.secrets.getSecretKey(spec = spec) }
    val secretKeyEncrypted = remember { App.injection.secrets.encrypt(publicKey, secretKey.encoded) }
    val secretKeyHash = remember { App.injection.secrets.sha256(secretKey.encoded).toHEX() }
    val payload = remember { System.currentTimeMillis().toString() }
    val encrypted = remember { App.injection.secrets.encrypt(secretKey, payload.toByteArray(), iv = iv) }
    val decrypted = remember { App.injection.secrets.decrypt(secretKey, encrypted, iv = iv) }
    val encryptedHash = remember { App.injection.secrets.sha256(encrypted) }
    LaunchedEffect(Unit) {
        println("password: ${String(spec.password)}")
        println("salt: ${spec.salt.toHEX()}")
        println("iterations: ${spec.iterationCount}")
        println("length: ${spec.keyLength}")
        println("iv: ${iv.toHEX()}")
        println("key: ${secretKey.encoded.toHEX()}")
    }
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
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "install id:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = installId,
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            Spacer(Modifier.weight(1f))
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "payload:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = payload,
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "encrypted:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = encrypted.toHEX(),
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "decrypted:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = String(decrypted),
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "encrypted hash:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = encryptedHash.toHEX(),
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        MediaScannerConnection.scanFile(context, arrayOf(App.injection.files.docs.absolutePath), arrayOf("*/*")) { _, _ ->
                            App.injection.files.docs.deleteRecursively()
                            App.injection.files.docs.mkdirs()
                            App.injection.files.docs.resolve("foo.key.enc").writeBytes(secretKeyEncrypted)
                            App.injection.files.docs.resolve("foo.payload.enc").writeBytes(encrypted)
                            val text = """
                                password: ${String(spec.password)}
                                salt: ${spec.salt.toHEX()}
                                iterations: ${spec.iterationCount}
                                length: ${spec.keyLength}
                                iv: ${iv.toHEX()}
                                key: ${secretKey.encoded.toHEX()}
                            """.trimIndent()
                            App.injection.files.docs.resolve("foo.spec.txt").writeText(text)
                        }
                    }
                    .wrapContentSize(),
                text = "export files",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SEND)
                        // todo
                    }
                    .wrapContentSize(),
                text = "share encrypted key",
                style = TextStyle(color = Color.Black),
            )
            Spacer(Modifier.weight(1f))
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "salt:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = spec.salt.toHEX(),
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "public key hash:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = publicKeyHash,
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "password:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = String(spec.password),
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = "secret key hash:",
                style = TextStyle(color = Color.Black),
            )
            BasicText(
                modifier = Modifier.fillMaxWidth(),
                text = secretKeyHash,
                style = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
            )
        }
    }
}
