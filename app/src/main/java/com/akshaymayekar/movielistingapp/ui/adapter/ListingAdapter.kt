package com.akshaymayekar.movielistingapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.akshaymayekar.movielistingapp.R
import com.akshaymayekar.movielistingapp.domain.model.Content
import com.akshaymayekar.movielistingapp.domain.model.Movie
import com.bumptech.glide.Glide

class ListingAdapter() : RecyclerView.Adapter<ListingAdapter.AssetViewHolder>() {
    lateinit var contentList: List<Content>

    constructor(contentList: List<Content>) : this() {
        this.contentList =  contentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return AssetViewHolder(v)
    }

    override fun onBindViewHolder(@NonNull holder: AssetViewHolder, position: Int) {
        holder.updateAsset(contentList[position])
    }

    override fun getItemCount(): Int {
        return contentList.size
    }


    inner class AssetViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val nameTextView: TextView
        private val ivPoster: ImageView
        private val view: View

        init {
            view = v
            nameTextView = v.findViewById(R.id.tv_name)
            ivPoster = v.findViewById(R.id.ivPoster)
        }

        fun updateAsset(content: Content) {
            nameTextView.text = content.name
            if (content.poster_image == "poster1.jpg") {
                ivPoster.setImageResource(R.drawable.poster1)
                Glide.with(view.context).load(R.drawable.poster1).into(ivPoster);
            }else if (content.poster_image == "poster2.jpg") {
                Glide.with(view.context).load(R.drawable.poster2).into(ivPoster);
            }else if (content.poster_image == "poster3.jpg") {
                Glide.with(view.context).load(R.drawable.poster3).into(ivPoster);
            }else if (content.poster_image == "poster4.jpg") {
                Glide.with(view.context).load(R.drawable.poster4).into(ivPoster);
            }else if (content.poster_image == "poster5.jpg") {
                Glide.with(view.context).load(R.drawable.poster5).into(ivPoster);
            }else if (content.poster_image == "poster6.jpg") {
                Glide.with(view.context).load(R.drawable.poster6).into(ivPoster);
            }else if (content.poster_image == "poster7.jpg") {
                Glide.with(view.context).load(R.drawable.poster7).into(ivPoster);
            }else if (content.poster_image == "poster8.jpg") {
                Glide.with(view.context).load(R.drawable.poster8).into(ivPoster);
            }else if (content.poster_image == "poster9.jpg") {
                Glide.with(view.context).load(R.drawable.poster9).into(ivPoster);
            }
        }

    }
}

