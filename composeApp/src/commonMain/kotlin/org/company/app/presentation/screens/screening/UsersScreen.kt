package org.company.app.presentation.screens.screening

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.company.app.domain.model.UserProfile
import org.koin.compose.koinInject

class UsersScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: UsersViewModel = koinInject()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onEvent(UsersEvent.LoadUsers)
        }

        MaterialTheme {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator()
                    }
                    state.error != null -> {
                        Text("Error: ${state.error}")
                    }
                    else -> {
                        LazyColumn {
                            items(state.users) { user ->
                                UserItem(user)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: UserProfile) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
        Text(text = "Age: ${user.age}", style = MaterialTheme.typography.bodyMedium)
    }
}