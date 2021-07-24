package it.prova.prima.spalla

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.koin.java.KoinJavaComponent.getKoin
import java.net.UnknownHostException

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

suspend fun httpTryCatch(onSuccess: suspend () -> Unit, onError: (String) -> Unit) {
    val context: Context by getKoin().inject()
    try {
        onSuccess()
    } catch (e: UnknownHostException) {
        onError(context.getString(R.string.network_error))
    } catch (t: Throwable) {
        onError(t.message ?: "")
    }
}