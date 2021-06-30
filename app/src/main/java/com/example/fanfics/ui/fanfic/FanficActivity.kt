package com.example.fanfics.ui.fanfic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import co.lujun.androidtagview.TagContainerLayout
import com.bumptech.glide.Glide
import com.example.fanfics.R
import com.example.fanfics.data.models.Fanfic

const val FANFIC_OBJ = "com.example.fanfics.data.models.Fanfic"

class FanficActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_fanfic)
        init()
        putData()
    }


    private fun onRead(id: Long){
        Toast.makeText(this, "Clicked id $id", Toast.LENGTH_SHORT).show()
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
            readButton.setOnClickListener { onRead(fanfic.id) }
            descriptionTextView.text = fanfic.description
            tagsTagsLayout.setTags(fanfic.tags.map { it.name })
        }
    }

    private fun init(){
        fanfic = intent?.getParcelableExtra(FANFIC_OBJ) as Fanfic?
        backgroundImageView = findViewById(R.id.blur_background)
        titleImageView = findViewById(R.id.fanfic_activity_image)
        titleTextView = findViewById(R.id.fanfic_activity_title)
        authorTextView = findViewById(R.id.fanfic_activity_author)
        readButton = findViewById(R.id.fanfic_activity_read_button)
        descriptionTextView = findViewById(R.id.fanfic_activity_description)
        tagsTagsLayout = findViewById(R.id.fanfic_activity_tags)
    }
}