package br.com.github.ui.common.customView
//
//import android.content.Context
//import android.transition.Fade
//import android.transition.TransitionManager
//import android.util.AttributeSet
//import android.view.inputmethod.EditorInfo
//import android.widget.FrameLayout
//import androidx.core.view.isVisible
//import androidx.core.view.updateLayoutParams
//import androidx.core.widget.doAfterTextChanged
//import br.com.github.databinding.LayoutSearchInputViewBinding
//
//class SearchInputView : FrameLayout {
//
//    private companion object {
//        private const val TYPING_DEBOUNCE_MS = 300L
//        private const val FADE_DURATION = 300L
//    }
//
//    private val binding: LayoutSearchInputViewBinding =
//        LayoutSearchInputViewBinding.inflate(streamThemeInflater, this, true)
//
//    private var debouncedInputChangedListener: InputChangedListener? = null
//    private var continuousInputChangedListener: InputChangedListener? = null
//    private var searchStartedListener: SearchStartedListener? = null
//
//    private val inputDebouncer = Debouncer(debounceMs = TYPING_DEBOUNCE_MS)
//
//    private lateinit var style: SearchInputViewStyle
//
//    private val query: String
//        get() = binding.inputField.text.trim().toString()
//
//    private var disableListeners = false
//
//    public constructor(context: Context) : super(context.createStreamThemeWrapper()) {
//        init(null)
//    }
//
//    public constructor(
//        context: Context,
//        attrs: AttributeSet?
//    ) : super(context.createStreamThemeWrapper(), attrs) {
//        init(attrs)
//    }
//
//    public constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context.createStreamThemeWrapper(),
//        attrs,
//        defStyleAttr
//    ) {
//        init(attrs)
//    }
//
//    private fun init(attrs: AttributeSet?) {
//        style = SearchInputViewStyle(context, attrs)
//
//        binding.root.setOnClickListener { binding.inputField.focusAndShowKeyboard() }
//        binding.root.updateLayoutParams {
//            this.height = style.searchInputHeight
//        }
//
//        binding.clearInputButton.setImageDrawable(style.clearInputDrawable)
//        binding.searchIcon.setImageDrawable(style.searchIconDrawable)
//        binding.inputField.hint = style.hintText
//        binding.inputField.setHintTextColor(style.hintColor)
//        binding.inputField.setTextColor(style.textColor)
//        binding.root.background = style.backgroundDrawable
//        binding.inputField.setTextSizePx(style.textSize.toFloat())
//
//        binding.inputField.doAfterTextChanged { newText ->
//            updateClearButtonVisibility(newText)
//
//            if (disableListeners) return@doAfterTextChanged
//
//            val newQuery = query
//            continuousInputChangedListener?.onInputChanged(newQuery)
//            inputDebouncer.submit {
//                debouncedInputChangedListener?.onInputChanged(newQuery)
//            }
//        }
//
//        binding.inputField.setOnEditorActionListener { _, actionId, _ ->
//            when (actionId) {
//                EditorInfo.IME_ACTION_SEARCH -> {
//                    searchStartedListener?.onSearchStarted(query)
//                    true
//                }
//
//                else -> false
//            }
//        }
//
//        binding.clearInputButton.setOnClickListener {
//            clear()
//        }
//
//        updateClearButtonVisibility(query)
//    }
//
//    private fun updateClearButtonVisibility(text: CharSequence?) {
//        val isClearButtonVisible = !text.isNullOrEmpty()
//        if (isClearButtonVisible && !binding.clearInputButton.isVisible) {
//            TransitionManager.beginDelayedTransition(
//                binding.root,
//                Fade().setDuration(FADE_DURATION)
//            )
//        }
//        binding.clearInputButton.isVisible = isClearButtonVisible
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        inputDebouncer.shutdown()
//    }
//
//    public fun setQuery(query: String) {
//        binding.inputField.setText(query.trim())
//    }
//
//    public fun clear(): Boolean {
//        if (query.isEmpty()) {
//            return false
//        }
//
//        withoutListenerNotifications {
//            binding.inputField.setText("")
//
//            // Notify both listeners instantly, manually
//            continuousInputChangedListener?.onInputChanged("")
//            debouncedInputChangedListener?.onInputChanged("")
//        }
//
//        return true
//    }
//
//    private inline fun withoutListenerNotifications(actions: () -> Unit) {
//        disableListeners = true
//        actions()
//        disableListeners = false
//    }
//
//    public fun setContinuousInputChangedListener(inputChangedListener: InputChangedListener?) {
//        this.continuousInputChangedListener = inputChangedListener
//    }
//
//    public fun setDebouncedInputChangedListener(inputChangedListener: InputChangedListener?) {
//        this.debouncedInputChangedListener = inputChangedListener
//    }
//
//    public fun setSearchStartedListener(searchStartedListener: SearchStartedListener?) {
//        this.searchStartedListener = searchStartedListener
//    }
//
//    public fun interface InputChangedListener {
//        public fun onInputChanged(query: String)
//    }
//
//    public fun interface SearchStartedListener {
//        public fun onSearchStarted(query: String)
//    }
//}
