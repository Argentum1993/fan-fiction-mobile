package com.example.fanfics.ui.user.fanfics

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.fanfics.data.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.fanfics.data.models.Fanfic
import javax.inject.Singleton


class UserFanficRepository @Inject constructor(private val apiService: ApiService) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    fun userFanficsFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Fanfic>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { UserFanficsPagingSource(apiService) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

}