package br.com.github.ui.common

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

    fun show(
        errorTitle: String? = null,
        errorMessage: String? = null
    ) {
        binding.run {
            tvError.text = errorTitle ?: root.context.getString(R.string.default_error_title)
            tvErrorDetail.text =
                errorMessage ?: root.context.getString(R.string.default_error_message)
            root.visibility = View.VISIBLE
        }
    }

    fun hide() {
        binding.root.visibility = View.GONE
    }
}
