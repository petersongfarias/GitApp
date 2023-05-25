package br.com.github.ui.common.extensions

import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import br.com.github.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import kotlin.math.roundToInt

fun ImageView.loadUserImage(url: String) {
    try {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions().override(100, 100)
                    .circleCrop()
                    .placeholder(R.drawable.ic_avata_placeholder_default)
                    .error(R.drawable.icon_error)
            )
            .into(this)
    } catch (e: GlideException) {
        e.logRootCauses("GlideException")
    }
}

fun View.changeVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

internal fun Drawable.applyTint(@ColorInt tintColor: Int?): Drawable {
    if (tintColor == null) return this

    val tintedDrawable = DrawableCompat.wrap(this).mutate()
    DrawableCompat.setTint(tintedDrawable, tintColor)
    DrawableCompat.setTintMode(tintedDrawable, PorterDuff.Mode.SRC_IN)
    return tintedDrawable
}

internal fun Int.dpToPx(): Int = dpToPxPrecise().roundToInt()

internal fun Int.dpToPxPrecise(): Float = (this * displayMetrics().density)

internal fun displayMetrics(): DisplayMetrics = Resources.getSystem().displayMetrics