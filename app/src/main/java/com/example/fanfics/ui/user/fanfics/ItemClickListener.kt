package com.example.fanfics.ui.user.fanfics

import com.example.fanfics.data.models.Fanfic

interface FanficClickListener {
    fun onCellClickListener(fanfic: Fanfic)
}