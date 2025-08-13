package com.example.animeworld.data.paging

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animeworld.data.remote.AnimeApi
import com.example.animeworld.data.remote.CharacterDto
import java.io.IOException

// ✅ UPDATED: Added query parameter
class CharacterPagingSource(
    private val api: AnimeApi,
    private val query: String // <-- ✅ NEW: Accept search query
) : PagingSource<Int, CharacterDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDto> {
        val page = params.key ?: 1

        return try {
            // ✅ UPDATED: Use query in API call
            val response = api.getCharacters(page = page, query = query) // <-- ✅ Pass query here
            val characters = response.data

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (characters.isEmpty()) null else page + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
