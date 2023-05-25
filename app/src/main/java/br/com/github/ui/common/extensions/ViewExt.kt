package br.com.github.ui.common.extensions

import android.view.View
import android.widget.ImageView
import br.com.github.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions

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
