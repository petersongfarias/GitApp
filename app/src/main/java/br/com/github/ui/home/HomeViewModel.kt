package br.com.github.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import br.com.github.domain.base.ResultState
import br.com.github.domain.useCase.UserDetailUseCase
import br.com.github.domain.useCase.UsersUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val usersUseCase: UsersUseCase,
    private val userDetailUseCase: UserDetailUseCase
) : ViewModel() {

    private val _errorSharedFlow = MutableSharedFlow<String>()
    val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    private val _loadingStateFlow = MutableStateFlow(false)
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    private val _homeStateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Nothing)
    val homeStateFlow = _homeStateFlow.asStateFlow()

    private var dispatchRetry: (() -> Unit)? = null

    fun fetchUsers() {
        dispatchRetry = { fetchUsers() }

        loadingStateChange(true)

        _homeStateFlow.value = HomeState.Nothing

        viewModelScope.launch {
            val userList = usersUseCase.invoke().flow.cachedIn(viewModelScope)
            _homeStateFlow.value = HomeState.ShowUserList(userList.first())
        }
    }

    fun fetchUser(searchQuery: String) {
        dispatchRetry = { fetchUser(searchQuery) }

        loadingStateChange(true)

        _homeStateFlow.value = HomeState.Nothing

        viewModelScope.launch {
            userDetailUseCase.invoke(searchQuery).collect {
                when (it) {
                    is ResultState.Success -> {
                        it.data?.let {
                            _homeStateFlow.emit(HomeState.ShowUser(it))
                        } ?: run {
                            _homeStateFlow.emit(HomeState.Nothing)
                        }
                        loadingStateChange(false)
                    }

                    is ResultState.Error -> {
                        _errorSharedFlow.emit(it.message.orEmpty())
                    }
                }
            }
        }
    }

    fun loadingStateChange(isLoading: Boolean) {
        _loadingStateFlow.value = isLoading
    }

    fun retryApiRequest() {
        dispatchRetry?.invoke()
    }
}
