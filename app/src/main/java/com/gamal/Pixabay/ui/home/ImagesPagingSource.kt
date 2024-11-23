package com.gamal.Pixabay.ui.home

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gamal.Pixabay.data.model.Hit
import com.gamal.Pixabay.data.repository.MainRepository
import com.gamal.Pixabay.utils.API_KEY


class ImagesPagingSource(
    private val  repository: MainRepository,
    private val query: String
) : PagingSource<Int, Hit>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val response = repository.getImages(
                key = API_KEY,
                q = query,
                image_type = "photo",
                page = page,
                per_page = 15)

            val data = response.hits
            Log.d("result", "data: ${data.size}")

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.d("result", "load: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
