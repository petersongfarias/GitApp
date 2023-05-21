package br.com.github.ui.home

import androidx.paging.PagingData
import br.com.github.domain.model.user.UserDetailModel
import br.com.github.domain.model.user.UserModel

sealed class HomeState {
    data class ShowUserList(val userList: PagingData<UserModel>) : HomeState()

    data class ShowUser(val user: UserDetailModel) : HomeState()

    object Nothing : HomeState()

    object Clear : HomeState()
}
