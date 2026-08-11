package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.FirebaseManager
import com.example.data.PlacesRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    placeId: String,
    onNavigateBack: () -> Unit
) {
    val place = PlacesRepository.getPlaceById(placeId)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(placeId) {
        val favorites = FirebaseManager.getFavorites()
        isFavorite = favorites.contains(placeId)
    }

    if (place == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Place not found")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(place.nameEn) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            isFavorite = !isFavorite
                            FirebaseManager.toggleFavorite(placeId, isFavorite)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = place.imageResId),
                contentDescription = place.nameEn,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = place.nameEn, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(text = place.nameKn, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.secondary)
                
                Spacer(modifier = Modifier.height(8.dp))
                Text("📍 ${place.location}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))
                Text("About this place", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(place.descriptionEn, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(place.descriptionKn, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))
                InfoRow("🌤 Best Time:", "${place.bestTimeEn} / ${place.bestTimeKn}")
                InfoRow("💰 Cost:", "${place.entryFeeEn} (Travel: ${place.travelCostEn})")
                InfoRow("🚗 Travel:", "${place.distance} - ${place.routeEn}")

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("geo:${place.latitude},${place.longitude}?q=${Uri.encode(place.nameEn)}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View on Google Maps")
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
