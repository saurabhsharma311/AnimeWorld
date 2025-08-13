package com.example.animeworld.presentation.CharacterDetail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.animeworld.data.local.FavoriteCharacterEntity
import com.example.animeworld.domain.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onAddFavorite: (FavoriteCharacterEntity) -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(text = state.error ?: "Unknown Error", textAlign = TextAlign.Center)
            }

            state.character != null -> {
                CharacterDetailContent(
                    character = state.character!!,
                    onAddFavorite = onAddFavorite
                )
            }
        }
    }
}

@Composable
fun CharacterDetailContent(
    character: Character,
    onAddFavorite: (FavoriteCharacterEntity) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = character.imageUrl,
            contentDescription = character.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val favorite = FavoriteCharacterEntity(
                    id = character.id,
                    name = character.name,
                    imageUrl = character.imageUrl
                )
                onAddFavorite(favorite)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add to Favorites")
        }

        Button(onClick = {
            coroutineScope.launch {
                shareCharacterWithImage(context, character)
            }
        }) {
            Text("Share Character")
        }
    }
}

suspend fun shareCharacterWithImage(context: Context, character: Character) {
    val imageLoader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(character.imageUrl)
        .allowHardware(false)
        .build()

    withContext(Dispatchers.IO) {
        try {
            val result = imageLoader.execute(request)

            val bitmap = when (val drawable = (result as? SuccessResult)?.drawable) {
                is BitmapDrawable -> drawable.bitmap
                else -> null
            }

            if (bitmap != null) {
                val cachePath = File(context.cacheDir, "shared_images")
                cachePath.mkdirs()

                val file = File(cachePath, "${character.name}.png")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }

                val contentUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )

                withContext(Dispatchers.Main) {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_STREAM, contentUri)
                        putExtra(Intent.EXTRA_TEXT, character.name)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
            } else {
                Log.e("shareDebug", "Bitmap was null")
            }
        } catch (e: Exception) {
            Log.e("shareDebug", "Error sharing image", e)
        }
    }
}
