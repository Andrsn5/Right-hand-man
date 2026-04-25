package org.company.app.presentation.screens.screening

import org.company.app.domain.model.UserProfile

data class UserState(
    val users: List<UserProfile> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)