package org.company.app.di

import org.company.app.data.remote.repository.AuthRepositoryImpl
import org.company.app.domain.repository.AuthRepository
import org.company.app.domain.usecase.AuthUseCase
import org.company.app.domain.usecase.GetUsersUseCase
import org.company.app.presentation.screens.login.LoginViewModel
import org.company.app.presentation.screens.registration.RegistrationViewModel
import org.company.app.presentation.screens.screening.UsersViewModel
import org.koin.dsl.module

private val domainModule = module {
    factory { AuthUseCase() }
    factory { GetUsersUseCase() }
}
private val dataModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl()
    }
}
private val presentationModule = module {
    single { LoginViewModel() }
    single { RegistrationViewModel() }
    single { UsersViewModel() }
}

private fun getAllModules() = listOf(
    domainModule, presentationModule,dataModule
)

fun getSharedModules() = getAllModules()