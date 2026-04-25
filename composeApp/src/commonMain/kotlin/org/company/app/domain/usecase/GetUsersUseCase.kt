package org.company.app.domain.usecase

import org.company.app.domain.model.UserProfile
import org.company.app.domain.repository.AuthRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetUsersUseCase : KoinComponent {
    private val repository: AuthRepository by inject()

    suspend operator fun invoke(): List<UserProfile> {
        return repository.getUsers()
    }
}