package com.example.fanfics.ui.fanfic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import co.lujun.androidtagview.TagContainerLayout
import com.bumptech.glide.Glide
import com.example.fanfics.R
import com.example.fanfics.data.models.Fanfic

class FanficMainFragment : Fragment() {

    private val fanficViewModel: FanficViewModel by activityViewModels()
    private var fanfic: Fanfic? = null

    private lateinit var backgroundImageView: ImageView
    private lateinit var titleImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var readButton: Button
    private lateinit var descriptionTextView: TextView
    private lateinit var tagsTagsLayout: TagContainerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fanfic = it.getParcelable(FANFIC_OBJ)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fanfic_main, container, false)
        init(view)
        putData()
        return view
    }

    private fun init(view: View){
        backgroundImageView = view.findViewById(R.id.blur_background)
        titleImageView = view.findViewById(R.id.fanfic_activity_image)
        titleTextView = view.findViewById(R.id.fanfic_activity_title)
        authorTextView = view.findViewById(R.id.fanfic_activity_author)
        readButton = view.findViewById(R.id.fanfic_activity_read_button)
        descriptionTextView = view.findViewById(R.id.fanfic_activity_description)
        tagsTagsLayout = view.findViewById(R.id.fanfic_activity_tags)
    }

    private fun putData(){
        fanfic?.let { fanfic ->
            Glide
                .with(this)
                .load(fanfic.img)
                .centerCrop()
                .into(backgroundImageView)
            Glide
                .with(this)
                .load(fanfic.img)
                .centerCrop()
                .into(titleImageView)
            titleTextView.text = fanfic.title
            authorTextView.text = HtmlCompat.fromHtml(
                "<b>Author:</b> ${fanfic.author}",
                HtmlCompat.FROM_HTML_MODE_LEGACY)
            readButton.setOnClickListener {
                fanficViewModel.readEvent.postValue(FanficMainFragment::class.simpleName)
            }
            descriptionTextView.text = fanfic.description
            tagsTagsLayout.tags = fanfic.tags.map { it.name }
        }
    }
}