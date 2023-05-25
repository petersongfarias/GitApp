package br.com.github.ui.common.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.github.R
import br.com.github.databinding.LayoutCustomErrorViewBinding

class CustomErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutCustomErrorViewBinding =
        LayoutCustomErrorViewBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)
    }

    fun setRetryClickListener(clickListener: OnClickListener) {
        binding.btnRetry.setOnClickListener(clickListener)
    }

    fun setError(
        errorTitle: String? = null,
        errorMessage: String? = null
    ) {
        with(binding) {
            tvError.text = errorTitle ?: root.context.getString(R.string.default_error_title)
            tvErrorDetail.text =
                errorMessage ?: root.context.getString(R.string.default_error_message)
        }
    }
}

fun CustomErrorView.show(
    errorTitle: String? = null,
    errorMessage: String? = null
) {
    setError(errorTitle, errorMessage)
    this.visibility = View.VISIBLE
}

fun CustomErrorView.hide() {
    this.visibility = View.GONE
}
