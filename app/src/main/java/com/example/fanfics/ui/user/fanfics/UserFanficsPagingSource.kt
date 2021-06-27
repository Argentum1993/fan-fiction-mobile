package com.example.fanfics.ui.user.fanfics

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.models.Fanfic
import com.example.fanfics.ui.user.fanfics.UserFanficRepository.Companion.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class UserFanficsPagingSource(val apiService: ApiService) : PagingSource<Int, Fanfic>() {

    override fun getRefreshKey(state: PagingState<Int, Fanfic>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Fanfic> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try{
            //TODO remove hardcode
                Log.d("PS", "Before fetch data")
            val response = apiService.getUserFanfic(1, page, params.loadSize)
            Log.d("PS", "After fetch data")
            LoadResult.Page(
                response,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }
}