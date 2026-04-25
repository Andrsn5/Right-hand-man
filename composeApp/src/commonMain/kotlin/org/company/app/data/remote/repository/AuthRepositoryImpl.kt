package org.company.app.data.remote.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.company.app.domain.model.UserProfile
import org.company.app.data.model.UserRemote
import org.company.app.domain.model.AppUser
import org.company.app.domain.repository.AuthRepository

class AuthRepositoryImpl: AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    override suspend fun doRegister(user: AppUser): Boolean {
        return try {
            val newUser = auth.createUserWithEmailAndPassword(user.email, user.password).user.let { fbUser ->
                if (fbUser != null) {
                    val firestoreUser = UserRemote(
                        uid = fbUser.uid,
                        email = user.email,
                        password = "HIDDEN",
                        registrationDate = user.registrationDate
                    )

                    firestore.collection("USERS").document(fbUser.uid)
                        .set(firestoreUser)
                    fbUser
                } else {
                    null
                }
            }
            newUser != null
        } catch (e: Exception) {
            println("doRegiset: ${e.message}")
            false
        }
    }

    override suspend fun doLogin(email: String, password: String): Boolean {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password)
            user.user != null
        } catch (e: Exception) {
            println("doLogin error: ${e.message}")
            false
        }
    }

    override suspend fun getUsers(): List<UserProfile> {
        return try {
            val response = firestore.collection("USERS").get()
            response.documents.map { document ->
                document.data<UserProfile>()
            }
        } catch (e: Exception) {
            println("getUsers error: ${e.message}")
            emptyList()
        }
    }

}