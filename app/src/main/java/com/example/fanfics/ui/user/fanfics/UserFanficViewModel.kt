package com.example.fanfics.ui.user.fanfics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fanfics.App
import com.example.fanfics.data.ApiService
import com.example.fanfics.data.models.Fanfic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserFanficViewModel constructor(
        val userFanficRepository: UserFanficRepository = App.appComponent.getUserFanficRepository()):
        ViewModel() {

    fun fetchUserFanfics(): Flow<PagingData<Fanfic>> {
        Log.d("fetchUserFanfics", "before fetch data from repo")

        val result = userFanficRepository.userFanficsFlow()
                .cachedIn(viewModelScope)
        Log.d("fetchUserFanfics", "after fetch data from repo")
        return result
    }
}