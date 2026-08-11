package com.example.model

data class Place(
    val id: String,
    val nameEn: String,
    val nameKn: String,
    val descriptionEn: String,
    val descriptionKn: String,
    val location: String,
    val category: String,
    val bestTimeEn: String,
    val bestTimeKn: String,
    val entryFeeEn: String,
    val entryFeeKn: String,
    val travelCostEn: String,
    val travelCostKn: String,
    val distance: String,
    val routeEn: String,
    val routeKn: String,
    val latitude: Double,
    val longitude: Double,
    val imageResId: Int,
    val isHiddenGem: Boolean = true
)

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

data class UserProfile(
    val uid: String,
    val displayName: String,
    val email: String,
    val photoUrl: String? = null
)
