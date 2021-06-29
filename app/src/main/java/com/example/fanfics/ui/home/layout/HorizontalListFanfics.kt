package com.example.fanfics.ui.home.layout

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class HorizontalListFanfics(
        var listRecyclerView: RecyclerView,
        var titleTextView: TextView,
        var descriptionTextView: TextView,
        var linearLayoutManager: LinearLayoutManager?,
        var layout: View
)
