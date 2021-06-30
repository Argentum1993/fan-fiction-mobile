package com.example.fanfics.ui.user.fanfics

import android.content.Intent
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
import com.example.fanfics.data.models.Fanfic
import com.example.fanfics.ui.fanfic.FANFIC_OBJ
import com.example.fanfics.ui.fanfic.FanficActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class UserFanficsFragment : Fragment(), FanficClickListener {

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

        userFanficViewModel =  defaultViewModelProviderFactory.create(UserFanficViewModel::class.java)
        adapter = UserFanficsAdapter(UserFanficsAdapter.diffCallback, this, this)
        (view as RecyclerView).adapter = adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            userFanficViewModel.fetchUserFanfics().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }

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

    override fun onCellClickListener(fanfic: Fanfic) {
        val intent = Intent(activity,  FanficActivity::class.java).apply {
            putExtra(FANFIC_OBJ, fanfic)
        }
        startActivity(intent)
    }
}