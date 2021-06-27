package com.example.fanfics.ui.user.fanfics

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.fanfics.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class UserFanficsFragment : Fragment() {

    private var columnCount = 1

    lateinit var userFanficViewModel: UserFanficViewModel
    lateinit var adapter: UserFanficsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_fanfics_list, container, false)

        Log.d("F", "before init viewmodel")
        userFanficViewModel =  defaultViewModelProviderFactory.create(UserFanficViewModel::class.java)
        Log.d("F", "after init viewmodel")

        Log.d("F", "before init adapter")
        adapter = UserFanficsAdapter(UserFanficsAdapter.diffCallback, this)
        Log.d("F", "after init adapter")
        Log.d("F", "before set adapter view")
        (view as RecyclerView).adapter = adapter
        Log.d("F", "after set adapter view")
        Log.d("F", "before set layoutManager")
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
            }
        }
        Log.d("F", "after set layoutManager")

// Activities can use lifecycleScope directly, but Fragments should instead use
// viewLifecycleOwner.lifecycleScope.
        Log.d("F", "before corutine")
        viewLifecycleOwner.lifecycleScope.launch {
            userFanficViewModel.fetchUserFanfics().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }

//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
//            }
//        }
        Log.d("F", "exit oncreate view")
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                UserFanficsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}