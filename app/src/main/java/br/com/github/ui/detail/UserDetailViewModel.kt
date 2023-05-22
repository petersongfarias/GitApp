package br.com.github.ui.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.github.domain.model.user.BaseUser
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.useCase.UserDetailUseCase
import br.com.github.ui.common.LiveDataSingleEvent
import br.com.github.ui.common.toLiveData
import kotlinx.coroutines.launch

class UserDetailViewModel(
    user: BaseUser,
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

    fun showUserDetail(user: BaseUser) {
        if (user is UserDetailModel) {
            _userDetailSuccessEvent.value = user
        } else {
            user.login?.let { login ->
                fetchUser(login)
            } ?: run {
                _userDetailFailureEvent.value = USER_INVALID
            }
        }
    }

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

    companion object {
        const val USER_INVALID = "Usuário selecionado não é valido."
    }
}
