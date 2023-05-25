package br.com.github.ui.common.customView

import android.content.Context
import android.transition.Fade
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import br.com.github.databinding.LayoutSearchInputViewBinding
import br.com.github.ui.common.extensions.focusAndShowKeyboard

class SearchInputView : FrameLayout {

    private companion object {
        private const val TYPING_DEBOUNCE_MS = 300L
        private const val FADE_DURATION = 300L
    }

    private val inputDebouncer = Debouncer(debounceMs = TYPING_DEBOUNCE_MS)
    private val binding: LayoutSearchInputViewBinding =
        LayoutSearchInputViewBinding.inflate(LayoutInflater.from(context), this, true)

    var debouncedInputChangedListener: ((String) -> Unit)? = null
    var continuousInputChangedListener: ((String) -> Unit)? = null
    var searchStartedListener: ((String) -> Unit)? = null

    private val query: String
        get() = binding.inputField.text?.trim().toString()

    private var disableListeners = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        binding.root.setOnClickListener { binding.inputField.requestFocus() }

        binding.inputField.doAfterTextChanged { newText ->
            updateClearButtonVisibility(newText)

            if (disableListeners) return@doAfterTextChanged

            val newQuery = query
            continuousInputChangedListener?.invoke(newQuery)
            inputDebouncer.submit {
                debouncedInputChangedListener?.invoke(newQuery)
            }
        }

        binding.inputField.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchStartedListener?.invoke(query)
                    true
                }

                else -> false
            }
        }

        binding.clearInputButton.setOnClickListener {
            clear()
        }

        updateClearButtonVisibility(query)
    }

    private fun updateClearButtonVisibility(text: CharSequence?) {
        val isClearButtonVisible = !text.isNullOrEmpty()
        if (isClearButtonVisible && !binding.clearInputButton.isVisible) {
            TransitionManager.beginDelayedTransition(
                binding.root,
                Fade().setDuration(FADE_DURATION)
            )
        }
        binding.clearInputButton.isVisible = isClearButtonVisible
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        inputDebouncer.shutdown()
    }

    fun setQuery(query: String) {
        binding.inputField.setText(query.trim())
    }

    fun clear(): Boolean {
        if (query.isEmpty()) {
            return false
        }

        withoutListenerNotifications {
            binding.inputField.setText("")
            continuousInputChangedListener?.invoke("")
            debouncedInputChangedListener?.invoke("")
        }

        return true
    }

    private inline fun withoutListenerNotifications(actions: () -> Unit) {
        disableListeners = true
        actions()
        disableListeners = false
    }
}
