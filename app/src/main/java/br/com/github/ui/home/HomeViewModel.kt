package br.com.github.ui.home

import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.github.data.base.getErrorMessage
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.model.user.UserModel
import br.com.github.domain.useCase.UserDetailUseCase
import br.com.github.domain.useCase.UsersUseCase
import br.com.github.ui.common.liveData.LiveDataSingleEvent
import br.com.github.ui.common.extensions.toLiveData
import kotlinx.coroutines.launch

class HomeViewModel(
    private val usersUseCase: UsersUseCase,
    private val userDetailUseCase: UserDetailUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _userListSuccessEvent = LiveDataSingleEvent<PagingData<UserModel>>()
    val userListSuccessEvent = _userListSuccessEvent.toLiveData()

    private val _userDetailSuccessEvent = LiveDataSingleEvent<UserDetailModel>()
    val userDetailSuccessEvent = _userDetailSuccessEvent.toLiveData()

    private val _userListFailureEvent = LiveDataSingleEvent<String>()
    val userListFailureEvent = _userListFailureEvent.toLiveData()

    private val _userDetailFailureEvent = LiveDataSingleEvent<String>()
    val userDetailFailureEvent = _userDetailFailureEvent.toLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading.toLiveData()

    fun updateLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun updateFetchUsersFailure(error: LoadState.Error) {
        _userListFailureEvent.value = error.getErrorMessage()
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            usersUseCase.invoke().flow.cachedIn(viewModelScope).collect {
                _userListSuccessEvent.value = it
            }
        }
    }

    fun fetchUser(searchQuery: String) {
        viewModelScope.launch {
            _isLoading.value = true
            userDetailUseCase.invoke(searchQuery)
                .onSuccess {
                    _isLoading.value = false
                    _userDetailSuccessEvent.value = it
                }
                .onFailure {
                    _isLoading.value = false
                    _userDetailFailureEvent.value = it.message
                }
        }
    }
}
