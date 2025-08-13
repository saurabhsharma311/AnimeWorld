package com.example.animeworld.presentation.CharacterList

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animeworld.R
import com.example.animeworld.domain.model.Character
import com.example.animeworld.presentation.common.LottieAnimationView
import com.example.animeworld.presentation.components.CharacterItem

@Composable
fun CharacterListScreen(
    navController: NavHostController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val characters: LazyPagingItems<Character> = viewModel.characters.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(characters.loadState.refresh) {
        if (characters.loadState.refresh is LoadState.NotLoading) {
            Toast.makeText(context, "Characters Loaded Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            // 🔍 Search Bar
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            when {
                characters.loadState.refresh is LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieAnimationView(
                            animationResId = R.raw.loading,
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }

                characters.itemCount == 0 && query.isNotBlank() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LottieAnimationView(
                            animationResId = R.raw.no_data,
                            modifier = Modifier.size(250.dp)
                        )
                        Text("No results found.")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                    ) {
                        items(characters.itemCount) { index ->
                            characters[index]?.let { character ->
                                CharacterItem(
                                    character = character,
                                    onClick = {
                                        navController.navigate("character_detail/${character.id}")
                                    }
                                )
                            }
                        }

                        if (characters.loadState.append is LoadState.Loading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        if (characters.loadState.append is LoadState.Error) {
                            item {
                                Text("Error loading more data", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
