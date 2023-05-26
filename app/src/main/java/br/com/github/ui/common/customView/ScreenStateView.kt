package br.com.github.ui.common.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.github.R
import br.com.github.databinding.LayoutScreenStateViewBinding

class ScreenStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutScreenStateViewBinding =
        LayoutScreenStateViewBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)
    }

    fun setRetryClickListener(clickListener: OnClickListener) {
        binding.btnRetry.setOnClickListener(clickListener)
    }

    fun setState(
        title: String? = null,
        message: String? = null,
        animationName: String? = null
    ) {
        with(binding) {
            animationName?.let { ivError.setAnimation(it) }
            tvTitle.text = title ?: root.context.getString(R.string.default_error_title)
            tvMessage.text =
                message ?: root.context.getString(R.string.default_error_message)
        }
    }
}

fun ScreenStateView.show(
    errorTitle: String? = null,
    errorMessage: String? = null,
    animationName: String? = null
) {
    setState(errorTitle, errorMessage)
    this.visibility = View.VISIBLE
}

fun ScreenStateView.hide() {
    this.visibility = View.GONE
}
