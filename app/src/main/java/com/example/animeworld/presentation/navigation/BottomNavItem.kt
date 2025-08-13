package com.example.animeworld.presentation.navigation

sealed class BottomNavItem(val route: String, val label: String, val icon: Int) {

    object CharacterList :
        BottomNavItem("character_list", "Characters", android.R.drawable.ic_menu_view)

    object Favorites : BottomNavItem("favorites", "Favorites", android.R.drawable.btn_star)
}