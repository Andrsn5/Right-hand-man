package org.company.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name: String,
    val age: Int? = null,
    val description: String,
    val imageUrl: String
)