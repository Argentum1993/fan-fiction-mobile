package com.example.fanfics.ui.fanfic.chapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fanfics.R
import com.example.fanfics.ui.fanfic.FanficViewModel

class ChaptersFragment : Fragment() {
    private val fanficViewModel: FanficViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chapters, container, false)

        fanficViewModel.chapters.observe(viewLifecycleOwner, {
            val recyclerView: RecyclerView = createRecycleView(view)
            val adapter = ChaptersFanficAdapter()
            recyclerView.adapter = adapter
            adapter.submitList(it)
        })
        return view
    }

    private inline fun createRecycleView(view: View): RecyclerView{
        val recyclerView: RecyclerView = view.findViewById(R.id.chapter_list)
        recyclerView.setHasFixedSize(true)
        with(recyclerView){
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
        }
        return  recyclerView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ChaptersFragment.
         */
        @JvmStatic
        fun newInstance() = ChaptersFragment()
    }
}