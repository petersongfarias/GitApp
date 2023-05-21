package br.com.github.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.ContentLoadingProgressBar
import br.com.github.R

class CustomLoadingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val loadingBar: ContentLoadingProgressBar

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.layout_loading_bar,
            this,
            false
        ) as FrameLayout
        addView(view)

        loadingBar = view.findViewById(R.id.loading_bar)
    }

    fun showLoadingBar(isShow: Boolean) {
        val visibilityRes = if (isShow) View.VISIBLE else View.GONE
        this.visibility = visibilityRes
        loadingBar.visibility = visibilityRes
    }
}
