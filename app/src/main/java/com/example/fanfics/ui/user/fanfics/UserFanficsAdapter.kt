package com.example.fanfics.ui.user.fanfics

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagContainerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fanfics.R
import com.example.fanfics.data.models.Fanfic
import com.google.gson.Gson
import kotlin.math.roundToInt

class UserFanficsAdapter(
    diffCallback: DiffUtil.ItemCallback<Fanfic>,
    var context: UserFanficsFragment,
    val fanficClickListener: FanficClickListener
):
    PagingDataAdapter<Fanfic, UserFanficsAdapter.FanficViewHolder>(diffCallback){
    override fun onBindViewHolder(holder: FanficViewHolder, position: Int) {
        var userFanfic = getItem(position)

        var gson = Gson()

        Log.d("onBindViewHolder", gson.toJson(userFanfic))
        if (userFanfic != null) {
            holder.bind(userFanfic, context)
            holder.itemView.setOnClickListener {
                fanficClickListener.onCellClickListener(userFanfic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FanficViewHolder {
        return FanficViewHolder.getInstance(parent)
    }

    class FanficViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val fanficTitleTextView: TextView = view.findViewById(R.id.fanfic_title)
        private val fanficImageView: ImageView = view.findViewById(R.id.fanfic_image)
        private val fanficDescriptionTextView: TextView = view.findViewById(R.id.fanfic_description)
        private val fanficFandomTextView: TextView = view.findViewById(R.id.fanfic_fandom)
        private val fanficDateTextView: TextView = view.findViewById(R.id.fanfic_publication_date)
        private val fanicTagsLayout: TagContainerLayout = view.findViewById(R.id.fanfic_tags_layout)

        companion object {
            fun getInstance(parent: ViewGroup): FanficViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.user_fanfic, parent, false)
                return FanficViewHolder(view)
            }
        }

        fun bind(fanfic: Fanfic, context: UserFanficsFragment){
            fanficTitleTextView.text = fanfic.title
            fanficDescriptionTextView.text = fanfic.description
            fanicTagsLayout.setTags(fanfic.tags.map { it.name })
            fanficFandomTextView.text = fanfic.fandom.name
            fanficDateTextView.text = fanfic.publicationDate
            var density = context.resources.displayMetrics.density.roundToInt()
            Glide
                .with(context)
                .load(fanfic.img)
                .apply(RequestOptions().override(70 * density, 90 * density))
                .into(fanficImageView)

        }
    }
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Fanfic>() {
            override fun areItemsTheSame(oldItem: Fanfic, newItem: Fanfic): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Fanfic, newItem: Fanfic): Boolean {
                return oldItem == newItem
            }
        }
    }
}