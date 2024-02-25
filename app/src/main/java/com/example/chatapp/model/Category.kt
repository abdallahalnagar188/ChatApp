package com.example.chatapp.model

import com.example.chatapp.R

data class Category(
    val id: String? = null,
    val name: String? = null,
    val imageId: Int? = R.drawable.icon_movie
) {
    companion object {
        const val MUSIC = "music"
        const val SPORTS = "sports"
        const val MOVIES = "movies"
        fun fromId(categoryId: String): Category {
            return when (categoryId) {
                MUSIC -> Category(MUSIC, "music", R.drawable.icon_movie)
                SPORTS -> Category(SPORTS, "sports", R.drawable.icon_movie)
                else -> Category(MOVIES, "movies", R.drawable.icon_movie)
            }
        }

        fun getCategoriesList(): List<Category> {
            return listOf(fromId(MUSIC), fromId(SPORTS), fromId(MOVIES))

        }
    }
}