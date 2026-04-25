package org.company.app.presentation.screens.screening

sealed interface UsersEvent {
    data object LoadUsers : UsersEvent
}