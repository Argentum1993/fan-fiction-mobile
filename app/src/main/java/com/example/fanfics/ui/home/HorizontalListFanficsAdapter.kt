package com.example.fanfics.ui.home

import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fanfics.R
import com.example.fanfics.data.models.Fanfic
import com.example.fanfics.ui.user.fanfics.UserFanficsFragment
import kotlin.math.roundToInt

class HorizontalListFanficsAdapter(val context: HomeFragment):
        ListAdapter<Fanfic, HorizontalListFanficsAdapter.FanficViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FanficViewHolder {
        return FanficViewHolder.getInstance(parent)
    }

    override fun onBindViewHolder(holder: FanficViewHolder, position: Int) {
        holder.bind(getItem(position), context, position, itemCount)
    }

    class FanficViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val fanficImageView: ImageView = view.findViewById(R.id.template_horizontal_list_image)

        companion object {
            fun getInstance(parent: ViewGroup): FanficViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.recommended_fanfic_item, parent, false)
                return FanficViewHolder(view)
            }
        }


        fun bind(fanfic: Fanfic, context: HomeFragment, position: Int, itemCount: Int){
            var density = context.getResources().getDisplayMetrics().density
            Glide
                    .with(context)
                    .load(fanfic.img)
                    .apply(
                            RequestOptions().override(
                                    (70 * density + 0.5f).roundToInt(),
                                    (90 * density + 0.5f).roundToInt()
                            )
                    )
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

    fun getItemPublic(position: Int): Fanfic{
        return getItem(position)
    }


}