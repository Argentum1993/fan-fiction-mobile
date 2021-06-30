package com.example.fanfics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _logout = MutableLiveData<String>()
    val logout = _logout

    private val _profile = MutableLiveData<String>()
    val profile = _profile
}