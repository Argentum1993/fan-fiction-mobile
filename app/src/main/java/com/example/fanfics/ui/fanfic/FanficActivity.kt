package com.example.fanfics.ui.fanfic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import co.lujun.androidtagview.TagContainerLayout
import com.bumptech.glide.Glide
import com.example.fanfics.R
import com.example.fanfics.data.models.Fanfic
import com.example.fanfics.ui.fanfic.chapter.ChaptersFragment
import kotlinx.coroutines.launch

const val FANFIC_OBJ = "com.example.fanfics.data.models.Fanfic"

class FanficActivity : AppCompatActivity() {
    private val fanficViewModel: FanficViewModel by viewModels()
    private var fanfic: Fanfic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fanfic)
        supportActionBar?.hide()
        fanfic =  intent?.getParcelableExtra(FANFIC_OBJ) as Fanfic?
        supportFragmentManager.commit {
            val bundle = bundleOf(FANFIC_OBJ to fanfic)
            add<FanficMainFragment>(R.id.fanfic_container, args = bundle)
            setReorderingAllowed(true)
        }

        fanficViewModel.readEvent.observe(this, {
            fanfic?.id?.let { it1 -> onRead(it1) }
        })
    }


    private fun onRead(id: Long){
        lifecycleScope.launch {
            if (fanficViewModel.loadChapters(id)) {
                supportFragmentManager.commit {
                    replace<ChaptersFragment>(R.id.fanfic_container)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            } else {
                Toast.makeText(
                    this@FanficActivity,
                    "Can't load chapters",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}