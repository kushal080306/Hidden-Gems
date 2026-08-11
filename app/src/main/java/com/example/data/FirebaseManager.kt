package com.example.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.model.UserProfile
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseManager {
    val auth: FirebaseAuth? by lazy { 
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            null
        }
    }
    
    val firestore: FirebaseFirestore? by lazy { 
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentUser(): UserProfile? {
        val user = auth?.currentUser
        return if (user != null) {
            UserProfile(
                uid = user.uid,
                displayName = user.displayName ?: "User",
                email = user.email ?: "",
                photoUrl = user.photoUrl?.toString()
            )
        } else {
            null
        }
    }

    suspend fun signInWithGoogle(context: Context, serverClientId: String): Result<UserProfile> {
        return try {
            val credentialManager = CredentialManager.create(context)
            
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(serverClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(context = context, request = request)
            val credential = result.credential

            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = auth?.signInWithCredential(authCredential)?.await()
                val user = authResult?.user
                
                if (user != null) {
                    Result.success(
                        UserProfile(
                            uid = user.uid,
                            displayName = user.displayName ?: "User",
                            email = user.email ?: "",
                            photoUrl = user.photoUrl?.toString()
                        )
                    )
                } else {
                    Result.failure(Exception("Authentication failed"))
                }
            } else {
                Result.failure(Exception("Unexpected credential type"))
            }
        } catch (e: GetCredentialCancellationException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth?.signOut()
    }

    suspend fun toggleFavorite(placeId: String, isFavorite: Boolean) {
        val userId = auth?.currentUser?.uid ?: return
        val ref = firestore?.collection("users")?.document(userId)?.collection("favorites")?.document(placeId) ?: return
        if (isFavorite) {
            ref.set(mapOf("timestamp" to System.currentTimeMillis())).await()
        } else {
            ref.delete().await()
        }
    }

    suspend fun getFavorites(): List<String> {
        val userId = auth?.currentUser?.uid ?: return emptyList()
        val snapshot = firestore?.collection("users")?.document(userId)?.collection("favorites")?.get()?.await() ?: return emptyList()
        return snapshot.documents.map { it.id }
    }
}
