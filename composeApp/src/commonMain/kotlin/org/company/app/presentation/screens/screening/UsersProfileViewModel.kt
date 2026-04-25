package org.company.app.presentation.screens.screening

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.company.app.domain.model.UserProfile
import org.company.app.domain.usecase.GetUsersUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UsersViewModel : ViewModel(), KoinComponent {
    private val getUsersUseCase: GetUsersUseCase by inject()
    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    fun onEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.LoadUsers -> loadUsers()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val users: List<UserProfile> = getUsersUseCase()
                _state.update { it.copy(users = users, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}