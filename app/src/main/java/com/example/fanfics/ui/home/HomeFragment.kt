package com.example.fanfics.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fanfics.R
import com.example.fanfics.ui.home.layout.HorizontalListFanfics

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recommendedLayout: HorizontalListFanfics
    private lateinit var randomLayout: HorizontalListFanfics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        homeViewModel = defaultViewModelProviderFactory.create(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recommendedLayout = createHorizontalList(root, R.id.home_recommendation)
        randomLayout = createHorizontalList(root, R.id.home_random)

        recommendedLayout.layout
                .findViewById<Button>(R.id.horizontal_list_detail)
                .setOnClickListener {
                    homeViewModel.currentRecommendedFanfic.value?.let { it1 -> onDetail(it1.id) }
                }

        randomLayout.layout
                .findViewById<Button>(R.id.horizontal_list_detail)
                .setOnClickListener {
                    homeViewModel.currentRandomFanfic.value?.let { it1 -> onDetail(it1.id) }
                }

        homeViewModel.recommendedFanfics.observe(viewLifecycleOwner,  {
            val adapter = putAdapter(recommendedLayout.listRecyclerView)
            adapter.submitList(it)
            if (it.isNotEmpty())
                homeViewModel.setCurrentRecommendedFanfic(it[0])
            recommendedLayout.listRecyclerView.addOnScrollListener(
                object: RecyclerView.OnScrollListener (){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val pos =  (recyclerView.layoutManager as LinearLayoutManager).
                                findFirstVisibleItemPosition()
                        Log.i("onScrolled", "pos = $pos")
                        if (pos != RecyclerView.NO_POSITION) {
                            val fanfic = (recyclerView.adapter as HorizontalListFanficsAdapter)
                                    .getItemPublic(pos)
                            homeViewModel.setCurrentRecommendedFanfic(fanfic)
                        }
                    }
                }
            )
        })

        homeViewModel.randomFanfics.observe(viewLifecycleOwner,  {
            val adapter = putAdapter(randomLayout.listRecyclerView)
            adapter.submitList(it)
            if (it.isNotEmpty())
                homeViewModel.setCurrentRandomFanfic(it[0])
            randomLayout.titleTextView.text = adapter.getItemPublic(0).title
            randomLayout.listRecyclerView.addOnScrollListener(
                    object: RecyclerView.OnScrollListener (){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val pos =  (recyclerView.layoutManager as LinearLayoutManager).
                            findFirstVisibleItemPosition()
                            if (pos != RecyclerView.NO_POSITION) {
                                val fanfic = (recyclerView.adapter as HorizontalListFanficsAdapter)
                                        .getItemPublic(pos)
                                homeViewModel.setCurrentRandomFanfic(fanfic)
                            }
                        }
                    }
            )
        })

        homeViewModel.currentRecommendedFanfic.observe(viewLifecycleOwner, {
            recommendedLayout.titleTextView.text = it.title
            recommendedLayout.descriptionTextView.text = it.description
        })

        homeViewModel.currentRandomFanfic.observe(viewLifecycleOwner, {
            randomLayout.titleTextView.text = it.title
            randomLayout.descriptionTextView.text = it.description
        })

        return root
    }

    private fun onDetail(id: Long){
        Toast.makeText(this.context, "come id $id", Toast.LENGTH_SHORT).show()
    }

    private inline fun createHorizontalList(view: View, idIncludeView: Int): HorizontalListFanfics{
        val root: View = view.findViewById(idIncludeView)

        var recyclerView: RecyclerView = root.findViewById(R.id.list_recommended)
        recyclerView.setHasFixedSize(true)

        return  HorizontalListFanfics(
            recyclerView,
            root.findViewById(R.id.title_recommended_fanfic) as TextView,
            root.findViewById(R.id.horizontal_list_description) as TextView,
            null,
            root
        )
    }

    private inline fun putAdapter(recyclerView: RecyclerView): HorizontalListFanficsAdapter{
        val adapter = HorizontalListFanficsAdapter(this)
        with(recyclerView){
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false)
        }
        recyclerView.adapter = adapter
        return adapter
    }
}