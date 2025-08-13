package com.example.animeworld.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animeapp.presentation.favorites.FavoritesScreen
import com.example.animeworld.presentation.CharacterDetail.CharacterDetailScreen
import com.example.animeworld.presentation.CharacterList.CharacterListScreen
import com.example.animeworld.presentation.components.BottomBar
import com.example.animeworld.presentation.favorites.FavoriteCharactersViewModel
@Composable
fun AppNavGraph(navController: NavHostController) {

    val favoriteViewModel: FavoriteCharactersViewModel = hiltViewModel()


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {

            // Show BottomBar only on main screens
            if (currentRoute in listOf("character_list", "favorites")) {
                BottomBar(navController,currentRoute)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "character_list",
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. Character List Screen
            composable("character_list") {
                CharacterListScreen(navController)
            }

            // 2. Character Detail Screen (no bottom bar here)
            composable("character_detail/{characterId}") { backStackEntry ->
                val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                characterId?.let {
                    CharacterDetailScreen(
                        characterId = it,
                        onAddFavorite = { favoriteCharacter ->
                            favoriteViewModel.addToFavorites(favoriteCharacter)
                        }
                    )
                }
            }

            // 3. Favorites Screen
            composable("favorites") {
                FavoritesScreen(
                    viewModel = favoriteViewModel,
                    onCharacterClick = { character ->
                        navController.navigate("character_detail/${character.id}")
                    }
                )
            }
        }
    }
}