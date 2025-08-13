package com.example.animeapp.presentation.favorites

import android.widget.Toast
import com.example.animeworld.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.animeworld.domain.model.Character
import com.example.animeworld.presentation.common.LottieAnimationView
import com.example.animeworld.presentation.favorites.FavoriteCharactersViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoriteCharactersViewModel = hiltViewModel(),
    onCharacterClick: (Character) -> Unit
) {
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsState()
    val context = LocalContext.current

    if (favoriteCharacters.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimationView(animationResId = R.raw.empty, modifier = Modifier.size(200.dp))
            Text(text = "No favorites yet!")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(favoriteCharacters) { character ->
                CharacterFavoriteItem(
                    character = character,
                    onClick = { onCharacterClick(character) },
                    onRemoveClick = {
                        viewModel.removeFromFavorites(character)
                        Toast.makeText(context, "${character.name} removed", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CharacterFavoriteItem(
    character: com.example.animeworld.domain.model.Character,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "ID: ${character.id}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onRemoveClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}
