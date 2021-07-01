package com.example.fanfics.ui.fanfic.chapter

import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fanfics.R
import com.example.fanfics.data.models.Chapter
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

class ChaptersFanficAdapter: ListAdapter<Chapter, ChaptersFanficAdapter.ChaptersFanficHolder>(
    ChaptersFanficAdapter.diffCallback
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChaptersFanficHolder {
       return ChaptersFanficHolder.getInstance(parent)
    }

    override fun onBindViewHolder(
        holder: ChaptersFanficHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
    
    class ChaptersFanficHolder(view: View):  RecyclerView.ViewHolder(view){
        private val nameChapterTextView: TextView = view.findViewById(R.id.chapter_name)
        private val textChapterTextView: TextView = view.findViewById(R.id.chapter_text)
        
        fun bind(chapter: Chapter){
            nameChapterTextView.text = convertToText(chapter.name)
            textChapterTextView.text = convertToText(chapter.text)
        }

        private inline fun convertToText(markdown: String): Spanned{
            val flavour = CommonMarkFlavourDescriptor()
            var parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(markdown)
            val html = HtmlGenerator(markdown, parsedTree, flavour).generateHtml()
            return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        
        companion object {
            fun getInstance(parent: ViewGroup): ChaptersFanficHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.chapter, parent, false)
                return ChaptersFanficHolder(view)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Chapter>() {
            override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
                return oldItem == newItem
            }
        }
    }
}