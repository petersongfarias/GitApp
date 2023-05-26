package br.com.github.ui.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.github.domain.model.repos.UserRepositoryModel
import br.com.github.domain.model.user.BaseUser
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.useCase.UserDetailUseCase
import br.com.github.domain.useCase.UserRepositoriesUseCase
import br.com.github.ui.common.extensions.toLiveData
import br.com.github.ui.common.liveData.LiveDataSingleEvent
import kotlinx.coroutines.launch

class UserDetailViewModel(
    user: BaseUser,
    private val userRepositoriesUseCase: UserRepositoriesUseCase,
    private val userDetailUseCase: UserDetailUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _user = MutableLiveData(user)
    val user: LiveData<BaseUser> = _user.toLiveData()

    private val _userDetailSuccessEvent = LiveDataSingleEvent<UserDetailModel>()
    val userDetailSuccessEvent = _userDetailSuccessEvent.toLiveData()

    private val _userDetailFailureEvent = LiveDataSingleEvent<String>()
    val userDetailFailureEvent = _userDetailFailureEvent.toLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading.toLiveData()

    private val _userRepositoriesSuccessEvent = LiveDataSingleEvent<List<UserRepositoryModel>>()
    val userRepositoriesSuccessEvent = _userRepositoriesSuccessEvent.toLiveData()

    private val _userRepositoriesFailureEvent = LiveDataSingleEvent<String>()
    val userRepositoriesFailureEvent = _userRepositoriesFailureEvent.toLiveData()

    private val _userRepositoriesEmptyEvent = LiveDataSingleEvent<Unit>()
    val userRepositoriesEmptyEvent = _userRepositoriesEmptyEvent.toLiveData()

    private val _isRepositoriesLoading = MutableLiveData<Boolean>()
    val isRepositoriesLoading: LiveData<Boolean> = _isRepositoriesLoading.toLiveData()

    fun fetchUser(login: String?) {
        login?.let {
            viewModelScope.launch {
                _isLoading.value = true
                userDetailUseCase.invoke(it)
                    .onSuccess {
                        _isLoading.value = false
                        _userDetailSuccessEvent.value = it
                    }
                    .onFailure {
                        _isLoading.value = false
                        _userDetailFailureEvent.value = it.message
                    }
            }
        } ?: run {
            _userDetailFailureEvent.value = USER_INVALID
        }
    }

    fun fetchUserRepositories(login: String?) {
        login?.let {
            viewModelScope.launch {
                _isRepositoriesLoading.value = true
                userRepositoriesUseCase.invoke(it)
                    .onSuccess {
                        _isRepositoriesLoading.value = false
                        _userRepositoriesSuccessEvent.value = it
                    }
                    .onFailure {
                        _isRepositoriesLoading.value = false
                        _userRepositoriesFailureEvent.value = it.message
                    }
            }
        } ?: run {
            _isRepositoriesLoading.value = false
            _userRepositoriesEmptyEvent.value = Unit
        }
    }

    companion object {
        const val USER_INVALID = "Usuário selecionado não é valido."
    }
}
